package Panels;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.SwingWorker;
import Algorithms.*;

public final class VisualizerPanel extends BasePanel {
    private final BaseSort sortArray;
    private final ArrayList<ISortingAlgorithm> sortQueue;

    /**
     * Creates the GUI
     * @param algorithms List of algorithms to run for visualisation
     * @param playSounds Whether or not you want the algorithm to play sounds
     * @param panel The main application
     */
    public VisualizerPanel(ArrayList<ISortingAlgorithm> algorithms, boolean playSounds, MainPanel panel) {
        super(panel);
        setLayout(new BorderLayout());
        sortArray = new BaseSort(playSounds);
        add(sortArray, BorderLayout.CENTER);
        sortQueue = algorithms;
    }

    private void longSleep() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    private void shuffleAndWait() {
        sortArray.shuffleArray();
        sortArray.resetColours();
        longSleep();
    }

    public void onOpen() {
        SwingWorker<Void, Void> swingWorker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                try {
                    Thread.sleep(250);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                for (ISortingAlgorithm algorithm : sortQueue) {
                    shuffleAndWait();

                    sortArray.setName(algorithm.getName());
                    sortArray.setAlgorithm(algorithm);

                    algorithm.runSort(sortArray);
                    sortArray.resetColours();
                    sortArray.highlightArray();
                    sortArray.resetColours();
                    longSleep();
                }
                return null;
            }

            @Override
            public void done() {
                panel.popScreen();
            }
        };

        swingWorker.execute();
    }
}
