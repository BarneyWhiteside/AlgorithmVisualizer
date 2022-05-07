package Algorithms;

import javax.swing.*;
import java.awt.*;

/*
Base class for sorting algorithm
 */

public class BaseSort extends JPanel {
    private int[] array = new int[1000];

    public BaseSort(){

    }

    @Override
    public void paintComponent(Graphics g){
        Graphics2D graphics = (Graphics2D)g;
        super.paintComponent(graphics);
        graphics.fillRect(50, 50, 50, 50);
    }
}
