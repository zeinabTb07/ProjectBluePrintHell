package view.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ButtonFactory {

    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 80;
    private static final Font DEFAULT_FONT = new Font("Monospaced", Font.BOLD, 40);
    private static final Color DEFAULT_BACKGROUND = Color.lightGray;
            //new Color(162, 210, 255);

    public static class Builder {
        private String text = "";
        private String iconPath = null;
        private Point position ;
        private Dimension size = new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        private ActionListener action = null;
        private Font font = DEFAULT_FONT;
        private Color background = DEFAULT_BACKGROUND;

        public Builder withText(String text) {
            this.text = text;
            return this;
        }

        public Builder withIcon(String iconPath) {
            this.iconPath = iconPath;
            return this;
        }

        public Builder atPosition(Point position) {
            this.position = position;
            return this;
        }

        public Builder withSize(Dimension size) {
            this.size = size;
            return this;
        }

        public Builder withAction(ActionListener action) {
            this.action = action;
            return this;
        }

        public Builder withFont(Font font) {
            this.font = font;
            return this;
        }

        public Builder withBackground(Color background) {
            this.background = background;
            return this;
        }

        public JButton build() {
            JButton button = new JButton(text);

            if (iconPath != null) {
                button.setIcon(new ImageIcon(iconPath));
            }

            button.setSize(size);
            if(position!=null){
                button.setLocation(position);
            }
            button.setFont(font);
            button.setBackground(background);

            if (action != null) {
                button.addActionListener(action);
            }

            return button;
        }
    }

    public static JButton createButton() {
        return new Builder().build();
    }
}