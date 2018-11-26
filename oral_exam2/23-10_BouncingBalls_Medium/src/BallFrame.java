import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class BallFrame extends JFrame {

    private int ballCount = 1;

    private Ball initBall;

    private ExecutorService runBall = Executors.newFixedThreadPool(20);

    BallFrame() {

        super("Bouncing Ball");

        setLayout(null);
        setSize(600, 600);
        setResizable(false);
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);

        initBall = new Ball();
        add(initBall);

        addMouseListener(newBallListener);

        setVisible(true);
    }

    private void addNewBall(){
        if (ballCount == 1){
            runBall.execute(initBall);
            ballCount++;
        }
        else if (ballCount < 20){
            Ball ball = new Ball();
            add(ball);
            runBall.execute(ball);
            ballCount++;
        }
        else{
            System.out.println("20 Ball Maximum, Sorry");
        }
    }

    MouseListener newBallListener = new MouseListener() {
        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {
            addNewBall();
        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    };

    class Ball extends JPanel implements Runnable {

        private final int VELOCITY_VARIATION = 2;

        private int vX = -2;
        private int vY = 2;

        private Random rand = new Random();

        private int corX;
        private int corY;

        private Color color = new Color(rand.nextInt(255),
                rand.nextInt(255), rand.nextInt(255));

        Ball(){
            setPreferredSize(new Dimension(80,80));
            setLayout(null);
            corX = rand.nextInt(500);
            corY = rand.nextInt(500);
            setBounds(corX, corY, 80, 80);
            revalidate();
            repaint();
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D)g;
            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.drawOval(0,0,75,75); //draws circle
            g.fillOval(0,0,75,75); //adds color to circle
        }

        @Override
        public void run() {
            while (true) {
                corX += vX;
                corY += vY;
                if (corX < 0) {
                    switchX();
                    corX = 0;
                } else if (corX > 520) {
                    switchX();
                    corX = 520;
                }
                if (corY < 0) {
                    switchY();
                    corY = 0;
                } else if (corY > 520) {
                    switchY();
                    corY = 520;
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                SwingUtilities.invokeLater(() -> {
                    setBounds(corX, corY, 80, 80);
                });
            }
        }

        private void switchX() {
            vX *= -1;
            Boolean isNeg = (vX < 0);
            int shift = rand.nextInt(VELOCITY_VARIATION);
            vX = (isNeg) ? (vX - shift) : (vX + shift);
            // If velocity in either direction is too large, reset it
            if (vX > 4 || vX < -4)
                vX = (isNeg) ? (-2) : (2);
        }

        private void switchY() {
            vY *= -1;
            Boolean isNeg = (vY < 0);
            int shift = rand.nextInt(VELOCITY_VARIATION);
            vY = (isNeg) ? (vY - shift) : (vY + shift);
            // If velocity in either direction is too large, reset it
            if (vY > 4 || vY < -4)
                vY = (isNeg) ? (-2) : (2);
        }
    }
}
