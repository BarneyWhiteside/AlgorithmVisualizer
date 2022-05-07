package Panels;

import java.awt.Dimension;

import javax.swing.JPanel;

public abstract class BasePanel extends JPanel {
        protected MainPanel panel;

        public BasePanel(MainPanel panel) {
            this.panel = panel;
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(MainPanel.WIN_WIDTH, MainPanel.WIN_HEIGHT);
        }

        public abstract void onOpen();

}
