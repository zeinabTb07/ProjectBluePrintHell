package client.view.ui;
import client.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ButtonBuilder {

    public static class Builder {
        private String text = "";
        private String iconPath = null;
        private Point position;
        private Dimension size = null;
        private ActionListener action = null;
        private Font font = null;
        private Color background = Color.LIGHT_GRAY;

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


            if (size == null) {
                int width = (int) (300 * Constants.SCALE);
                int height = (int) (80 * Constants.SCALE);
                size = new Dimension(width, height);
            }
            button.setSize(size);


            if (position != null) {
                button.setLocation(position);
            }


            if (font == null) {
                font = new Font("Monospaced", Font.BOLD, (int) (40 * Constants.SCALE));
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
