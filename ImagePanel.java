 import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

class ImagePanel extends JPanel {
        private Image image;

        public ImagePanel(String image) {
                this(new ImageIcon(image).getImage());
        }

        public ImagePanel(Image image) {
                this.image = image;
                Dimension size = new Dimension(image.getWidth(null), image.getHeight(null));
                setPreferredSize(size);
                setMinimumSize(size);
                setMaximumSize(size);
                setSize(size);
                setLayout(null);
        }

        public void paintComponent(Graphics graphics) {
                graphics.drawImage(image, 0, 0, null);
        }
}