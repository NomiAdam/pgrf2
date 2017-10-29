package view;

import model.*;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

/**
 * trida pro kresleni na platno
 *
 * @author PGRF FIM UHK
 * @version 2017
 */

public class Canvas {
    private JFrame frame;
    private JPanel panel;
    private BufferedImage img;

    private LineRenderer lr;
    private CircleRenderer cr;
    private PolygonRenderer pr;
    private SeedFiller sf;
    private SeedFillerPatern sfp;

    private static final int YELLOW_COLOR = 0xffff00;
    private static final int RED_COLOR = 0xff0000;
    private static final int GREEN_COLOR = 0x00ff00;

    private int x1;
    private int x2;
    private int y1;
    private int y2;
    private int imageType;
    private boolean circleSector;

    private List<Point> points;
    private List<Point> lines;

    public Canvas(int width, int height) {
        frame = new JFrame();

        frame.setLayout(new BorderLayout());
        frame.setTitle("UHK FIM PGRF1 - Adam Kvasnička");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        lr = new LineRenderer(img);
        pr = new PolygonRenderer(img);
        cr = new CircleRenderer(img);
        sfp = new SeedFillerPatern(img);
        sf = new SeedFiller(img);

        points = new ArrayList<>();
        lines = new ArrayList<>();

        panel = new JPanel() {
            private static final long serialVersionUID = 1L;

            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                present(g);
            }
        };

        KeyAdapter key = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_L) {
                    imageType = 0;
                    lines.clear();
                } else if (e.getKeyCode() == KeyEvent.VK_P) {
                    imageType = 1;
                    points.clear();
                } else if (e.getKeyCode() == KeyEvent.VK_C) {
                    imageType = 2;
                } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    JOptionPane.showMessageDialog(
                            frame,
                            "Pro vykreslení kružnice:" +
                                    "\nPo prvním kliku a následném tažení myši po plátně se začne vykreslovat kružnice s proměným poloměrem R." +
                                    "\nPo druhém kliku a následném tažení myši po plátně se od místa kliku začne vykreslovat výseč kružnice",
                            "Vykreslení kružnice",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                }
                clear();
                panel.repaint();
            }
        };

        MouseAdapter mouse = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                x1 = e.getX();
                y1 = e.getY();
                if (e.getButton() == MouseEvent.BUTTON3) {
                    sfp.setBackColor(img.getRGB(x1, y1));
                    sfp.seed(x1, y1);
                } else {
                    switch (imageType) {
                        case 0:
                            lines.add(new Point(x1, y1));
                            break;
                        case 1:
                            if (points.size() < 1) {
                                points.add(new Point(x1, y1));
                            }
                            break;
                        case 2:
                            if (!circleSector) {
                                cr.setCenter(x1, y1);
                            } else {
                                cr.setStartingAnglePoints(x1, y1);
                            }
                            break;
                    }
                }
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {

                } else {
                    clear();
                    x2 = e.getX();
                    y2 = e.getY();
                    switch (imageType) {
                        case 0:
                            lr.reDraw(lines);
                            lr.setColor(RED_COLOR);
                            lr.draw(x1, x2, y1, y2);
                            break;
                        case 1:
                            lr.setColor(GREEN_COLOR);
                            if (points.size() > 1) {
                                lr.draw((int) points.get(points.size() - 1).getX(), x2, (int) points.get(points.size() - 1).getY(), y2);
                            }
                            lr.setColor(RED_COLOR);
                            lr.draw((int) points.get(0).getX(), x2, (int) points.get(0).getY(), y2);
                            pr.drawPolygon(points);
                            break;
                        case 2:
                            if (!circleSector) {
                                cr.setBorder(x2, y2);
                                cr.drawCircle();
                                lr.setColor(RED_COLOR);
                                lr.draw(x1, x2, y1, y2);
                            } else {
                                cr.setAngle(x2, y2);
                                cr.drawCircle();
                                lr.setColor(GREEN_COLOR);
                                lr.draw(x1, x2, y1, y2);
                            }
                            break;
                    }
                    lr.setColor(YELLOW_COLOR);
                    panel.repaint();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {

                } else {
                    clear();
                    x2 = e.getX();
                    y2 = e.getY();
                    switch (imageType) {
                        case 0:
                            lines.add(new Point(x2, y2));
                            lr.reDraw(lines);
                            break;
                        case 1:
                            points.add(new Point(x2, y2));
                            pr.drawPolygon(points);
                            break;
                        case 2:
                            if (!circleSector) {
                                cr.setBorder(x2, y2);
                                cr.drawCircle();
                                circleSector = true;
                            } else {
                                cr.drawCircle();
                                cr.setStartingAnglePoints();
                                circleSector = false;
                            }
                            break;
                    }
                }
                panel.repaint();
            }
        };

        panel.addMouseListener(mouse);
        panel.addMouseMotionListener(mouse);

        frame.addKeyListener(key);

        panel.setPreferredSize(new Dimension(width, height));

        frame.add(panel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }

    public void clear() {
        Graphics gr = img.getGraphics();
        gr.setColor(new Color(0x2f2f2f));
        gr.fillRect(0, 0, img.getWidth(), img.getHeight());
        gr.setColor(new Color(YELLOW_COLOR));
        gr.drawString("Press: L - for lines, P - for polygon, C - for circle (press ENTER for help)", 5, img.getHeight() - 5);
    }

    public void present(Graphics graphics) {
        graphics.drawImage(img, 0, 0, null);
    }

    public void start() {
        clear();
        panel.repaint();
    }

}
