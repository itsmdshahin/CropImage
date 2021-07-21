 
 import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class CropImage extends JFrame implements MouseListener, MouseMotionListener, ActionListener {
        int x1, y1, x2, y2;
        boolean isDragged = false;

        JMenuBar menuBar;
        JMenu fileMenu;
        JMenuItem loadFromFile, saveAs, quit;
        JLabel messageLabel;
        JPanel buttonPanel;
        JButton cropButton, clearButton;
        MouseEvent selectionEvent = null;
        JPanel imageJPanel;

        public static void main(String args[]) {
                new CropImage().start();
        }

        public void start() {
                setLayout(new BorderLayout());
                loadFromFile = new JMenuItem("Load From File");
                saveAs = new JMenuItem("Save as");
                quit = new JMenuItem("Quit");

                loadFromFile.addActionListener(this);
                saveAs.addActionListener(this);
                quit.addActionListener(this);

                menuBar = new JMenuBar();
                fileMenu = new JMenu("File");

                fileMenu.add(loadFromFile);
                fileMenu.add(saveAs);
                fileMenu.add(quit);

                menuBar.add(fileMenu);

                add(menuBar);
                setJMenuBar(menuBar);

                messageLabel = new JLabel("Message Label");
                add(messageLabel, BorderLayout.NORTH);

                buttonPanel = new JPanel();
                buttonPanel.setLayout(new GridLayout(1, 2, 30, 5));
                cropButton = new JButton("Crop");
                clearButton = new JButton("Clear");
                buttonPanel.add(cropButton);
                buttonPanel.add(clearButton);
                add(buttonPanel, BorderLayout.SOUTH);

                cropButton.addActionListener(this);

                setSize(1200, 800);
                setVisible(true);
                addMouseListener(this);
                addMouseMotionListener(this);
                setDefaultCloseOperation(EXIT_ON_CLOSE);
        }

        public void cropSelection() throws Exception {
                int width = x1 - x2;
                int height = y1 - y2;
                width = width * -1;
                height = height * -1;
                Robot robot = new Robot();
                BufferedImage img = robot.createScreenCapture(new Rectangle(x1, y1, width, height));
                File save_path = new File("C:\\Users\\emon\\Downloads\\messi.jpg");
                ImageIO.write(img, "JPG", save_path);
                messageLabel.setText("Crop successful");

                ImagePanel ip = new ImagePanel("C:\\Users\\emon\\Downloads\\messi.jpg");
                add(ip);

                imageJPanel.removeAll();
                add(imageJPanel);
        }

        @Override
        public void mouseClicked(MouseEvent me) {
        }

        @Override
        public void mouseEntered(MouseEvent me) {
        }

        @Override
        public void mouseExited(MouseEvent me) {
        }

        @Override
        public void mousePressed(MouseEvent me) {
                repaint();
                x1 = me.getX();
                y1 = me.getY();
        }

        @Override
        public void mouseReleased(MouseEvent me) {
                selectionEvent = me;
        }

        @Override
        public void mouseDragged(MouseEvent me) {
                repaint();
                isDragged = true;
                x2 = me.getX();
                y2 = me.getY();
        }

        @Override
        public void mouseMoved(MouseEvent me) {
        }

        public void paint(Graphics g) {
                super.paint(g);
                int w = x1 - x2;
                int h = y1 - y2;
                w = w * -1;
                h = h * -1;
                if (w < 0)
                        w = w * -1;
                g.drawRect(x1, y1, w, h);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
                if (e.getSource() == loadFromFile) {
                        JFileChooser fc = new JFileChooser();
                        int i = fc.showOpenDialog(this);
                        if (i == JFileChooser.APPROVE_OPTION) {
                                File f = fc.getSelectedFile();
                                String filepath = f.getPath();
                                ImagePanel ip = new ImagePanel(filepath);

                                imageJPanel = new JPanel();
                                imageJPanel.add(ip);

                                add(imageJPanel);
                                messageLabel.setText(f.getName() + " loaded");

                                try {
                                        BufferedImage img = ImageIO.read(f);
                                        int width = img.getWidth();
                                        int height = img.getHeight();
                                        setSize(width, height);
                                } catch (IOException e1) {
                                        e1.printStackTrace();
                                }
                        }

                }

                if (e.getSource() == quit) {
                        System.exit(0);
                }

                if (e.getSource() == cropButton) {
                        repaint();
                        if (isDragged) {
                                x2 = selectionEvent.getX();
                                y2 = selectionEvent.getY();
                                try {
                                        cropSelection();
                                } catch (Exception ex) {
                                        ex.printStackTrace();
                                }
                        }
                }
        }
} 