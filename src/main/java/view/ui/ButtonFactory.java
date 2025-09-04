package view.ui;
import model.constants.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ButtonFactory {

    // Removed fixed defaults, will calculate in Builder dynamically

    public static class Builder {
        private String text = "";
        private String iconPath = null;
        private Point position;
        private Dimension size = null; // will default in build()
        private ActionListener action = null;
        private Font font = null; // default in build()
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

            // Icon
            if (iconPath != null) {
                button.setIcon(new ImageIcon(iconPath));
            }

            // Size
            if (size == null) {
                int width = (int) (300 * Constants.SCALE);
                int height = (int) (80 * Constants.SCALE);
                size = new Dimension(width, height);
            }
            button.setSize(size);

            // Position
            if (position != null) {
                button.setLocation(position);
            }

            // Font
            if (font == null) {
                font = new Font("Monospaced", Font.BOLD, (int) (40 * Constants.SCALE));
            }
            button.setFont(font);

            button.setBackground(background);

            // Action
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
