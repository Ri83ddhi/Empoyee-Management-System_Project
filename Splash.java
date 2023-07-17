import javax.imageio.ImageIO;
import javax.swing.*;

import EMS.index;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

class Splash implements Runnable {
    private JFrame fr;
    private JProgressBar pb;
    private JLabel pl;
    private int progress = 0;

    public Splash() {
        fr = new JFrame();
        fr.setSize(960, 750);
        fr.setLocationRelativeTo(null);
        fr.setLayout(null);
        fr.setUndecorated(true);

        MyJPanel panel = new MyJPanel("images/splash.jpg");
        panel.setBounds(0, 0, 960, 720);
        panel.setBorder(BorderFactory.createEtchedBorder(Color.RED, Color.RED));
        panel.setLayout(null);

        pl = new JLabel(new ImageIcon("images/progress.gif"));
        pl.setBounds((960 - 256) / 2, 500, 256, 256);
        panel.add(pl);

        JPanel progressPanel = new JPanel();
        progressPanel.setBounds(0, 720, 960, 30);
        progressPanel.setLayout(null);
        progressPanel.setBorder(BorderFactory.createEtchedBorder(Color.RED, Color.RED));
        fr.add(progressPanel);

        pb = new JProgressBar();
        pb.setValue(progress);
        pb.setBounds(1, 1, 958, 28);
        pb.setStringPainted(true);
        progressPanel.add(pb);

        fr.add(panel);
        fr.setVisible(true);
        Thread th = new Thread(this);
        th.start();
    }

    public void run() {
        try {
            while (progress <= 100) {
                Thread.sleep(10);
                progress++;
                pb.setValue(progress);
            }
        } catch (InterruptedException ie) {
        }
        fr.dispose();
        new index();
    }

    class MyJPanel extends JPanel {
        private BufferedImage image;
        private int w, h;

        MyJPanel(String fname) {
            //reads the image
            try {
                image = ImageIO.read(new File(fname));
                w = image.getWidth();
                h = image.getHeight();
            } catch (IOException ioe) {
                System.out.println("Could not read in the pic");
                //System.exit(0);
            }
        }

        public Dimension getPreferredSize() {
            return new Dimension(w, h);
        }

        //this will draw the image
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(image, 0, 0, this);
        }
    }

    public static void main(String[] args) {
        new Splash();
    }
}
