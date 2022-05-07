package Algorithms;

import Util.MidiSoundPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/*
Base class for drawing the interactions
 */

public class BaseSort extends JPanel {
    public static final int WIN_WIDTH = 800;
    public static final int WIN_HEIGHT = 600;
    public static final int BAR_WIDTH = 5;
    public static final double BAR_HEIGHT_PERCENT = (WIN_WIDTH / 2.0) / WIN_HEIGHT;
    public static final int NUM_BARS = WIN_WIDTH/BAR_WIDTH;
    private final int[] array;
    private final int[] barColours;
    private String algorithmName = "";
    private ISortingAlgorithm algorithm;
    private long algorithmDelay = 0;
    private int arrayChanges = 0;
    private final MidiSoundPlayer player;
    private final JSpinner spinner;
    private final boolean playSounds;

    public BaseSort(boolean playSounds){
        setBackground(Color.gray);
        array = new int[NUM_BARS];
        barColours = new int[NUM_BARS];
        for(int i = 0; i < NUM_BARS; i++) {
            array[i] = i;
            barColours[i] = 0;
        }
        player = new MidiSoundPlayer(NUM_BARS);
        this.playSounds = playSounds;
        spinner = new JSpinner(new SpinnerNumberModel(1, 1, 1000, 1));
        spinner.addChangeListener((event) -> {
            algorithmDelay = (Integer) spinner.getValue();
            if(algorithm != null){
                algorithm.setDelay(algorithmDelay);
            }
        });
        add(spinner,BorderLayout.LINE_START);
    }

    public void shuffleArray(){
        arrayChanges = 0;
        Random rng = new Random();
        for(int i = 0; i < getArraySize(); i++){
            int swapWithIndex = rng.nextInt(getArraySize() - 1);
            swap(i, swapWithIndex, 4, false);
        }
        arrayChanges = 0;
    }

    public void swap(int i, int j, int delay, boolean isStep){
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;

        barColours[i] = 100;
        barColours[j] = 100;

        finaliseUpdate((array[i] + array[j])/2, delay, isStep);
    }

    public void updateSingle(int index, int value, long delay, boolean isStep) {
        array[index] = value;
        barColours[index] = 100;
        finaliseUpdate(value, delay, isStep);
        repaint();
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D panelGraphics = (Graphics2D) g.create();

        try
        {
            Map<RenderingHints.Key, Object> renderingHints = new HashMap<>();
            renderingHints.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            panelGraphics.addRenderingHints(renderingHints);
            panelGraphics.setColor(Color.WHITE);
            panelGraphics.setFont(new Font("Monospaced", Font.BOLD, 20));
            panelGraphics.drawString(" Current algorithm: " + algorithmName, 10, 30);
            panelGraphics.drawString("Current step delay: " + algorithmDelay + "ms", 10, 55);
            panelGraphics.drawString("     Array Changes: " + arrayChanges, 10, 80);

            drawBars(panelGraphics);
        } finally {
            panelGraphics.dispose();
        }
    }

    private void drawBars(Graphics2D panelGraphics)
        {
            int barWidth = getWidth() / NUM_BARS;
            int bufferedImageWidth = barWidth * NUM_BARS;
            int bufferedImageHeight = getHeight();

            if(bufferedImageHeight > 0 && bufferedImageWidth > 0) {
                if(bufferedImageWidth < 256) {
                    bufferedImageWidth = 256;
                }

                double maxValue = getMAXValue();

                BufferedImage bufferedImage = new BufferedImage(bufferedImageWidth, bufferedImageHeight, BufferedImage.TYPE_INT_ARGB);
                makeBufferedImageTransparent(bufferedImage);
                Graphics2D bufferedGraphics = null;
                try
                {
                    bufferedGraphics = bufferedImage.createGraphics();

                    for (int x = 0; x < NUM_BARS; x++) {
                        double currentValue = getValue(x);
                        double percentOfMax = currentValue / maxValue;
                        double heightPercentOfPanel = percentOfMax * BAR_HEIGHT_PERCENT;
                        int height = (int) (heightPercentOfPanel * (double) getHeight());
                        int xBegin = x + (barWidth - 1) * x;
                        int yBegin = getHeight() - height;

                        int val = barColours[x] * 2;
                        if (val > 190) {
                            bufferedGraphics.setColor(new Color(255 - val, 255, 255 - val));
                        }
                        else {
                            bufferedGraphics.setColor(new Color(255, 255 - val, 255 - val));
                        }
                        bufferedGraphics.fillRect(xBegin, yBegin, barWidth, height);
                        if (barColours[x] > 0) {
                            barColours[x] -= 5;
                        }
                    }
                }
                finally
                {
                    if(bufferedGraphics != null)
                    {
                        bufferedGraphics.dispose();
                    }
                }

                panelGraphics.drawImage(bufferedImage, 0, 0, getWidth(), getHeight(), 0, 0, bufferedImage.getWidth(), bufferedImage.getHeight(), null);
            }
        }

        private void makeBufferedImageTransparent(BufferedImage image)
        {
            Graphics2D bufferedGraphics = null;
            try
            {
                bufferedGraphics = image.createGraphics();

                bufferedGraphics.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR));
                bufferedGraphics.fillRect(0, 0, image.getWidth(), image.getHeight());
                bufferedGraphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
            }
            finally
            {
                if(bufferedGraphics != null)
                {
                    bufferedGraphics.dispose();
                }
            }
        }

    @Override
    public void setName(String algorithmName) {
        this.algorithmName = algorithmName;
    }

    public void setAlgorithm(ISortingAlgorithm algorithm) {
        this.algorithm = algorithm;
        algorithmDelay = algorithm.getDelay();
    }

    @Override
    public Dimension getPreferredSize(){
        return new Dimension(WIN_WIDTH, WIN_HEIGHT);
    }

    public int getArraySize(){
        return array.length;
    }

    public int getValue(int index){
        return array[index];
    }

    public double getMAXValue(){
        return Arrays.stream(array).max().orElse(Integer.MIN_VALUE);
    }

    public void resetColours() {
        for (int i = 0; i < NUM_BARS; i++) {
            barColours[i] = 0;
        }
        repaint();
    }

    public void highlightArray() {
        for (int i = 0; i < getArraySize(); i++) {
            updateSingle(i, getValue(i), 5, false);
        }
    }

    private void finaliseUpdate(int value, long millisecondDelay, boolean isStep) {
        repaint();
        try {
            Thread.sleep(millisecondDelay);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        if (playSounds) {
            player.makeSound(value);
        }
        if (isStep)
            arrayChanges++;
    }
}
