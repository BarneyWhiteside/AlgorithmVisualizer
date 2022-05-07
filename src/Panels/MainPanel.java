package Panels;

import javax.swing.*;
import java.util.ArrayList;

public class MainPanel {

    public static final int WIN_WIDTH = 800;
    public static final int WIN_HEIGHT = 600;

    private final ArrayList<BasePanel> panels;

    private final JFrame window;

    public MainPanel(String title) {
        panels = new ArrayList<>();
        window = new JFrame(title);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(WIN_WIDTH, WIN_HEIGHT);
        window.setVisible(true);
    }

    public BasePanel getCurrentScreen() {
        return panels.get(panels.size() - 1);
    }

    public void pushScreen(BasePanel panel) {
        if (!panels.isEmpty()) {
            window.remove(getCurrentScreen());
        }
        panels.add(panel);
        window.setContentPane(panel);
        window.validate();
        panel.onOpen();
    }

    public void popScreen() {
        if (!panels.isEmpty()) {
            BasePanel prev = getCurrentScreen();
            panels.remove(prev);
            window.remove(prev);
            if (!panels.isEmpty()) {
                BasePanel current = getCurrentScreen();
                window.setContentPane(current);
                window.validate();
                current.onOpen();
            }
            else {
                window.dispose();
            }
        }
    }

    public void start() {
        pushScreen(new MenuPanel(this));
        window.pack();
    }

    public static void main(String[] args) {
        System.setProperty("sun.java2d.opengl", "true");
        SwingUtilities.invokeLater(() -> new MainPanel("Algorithm Visualizer").start());
    }
}
