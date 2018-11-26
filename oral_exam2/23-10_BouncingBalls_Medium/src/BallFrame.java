import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * BallFrame is an extension of the {@link JFrame} class that initializes a thread
 * for each of the possible twenty 20 {@link Ball} runnable objects, and presents
 * a space on the screen to view their movement. It also binds the {@link #mouseListener}
 * {@link #newBallListener} to "mousePressed" events on the frame
 * as required by the problem instructions. It also holds the count of all
 * active balls (or threads) currently active.
 *
 * @see JFrame
 * @see Ball
 * @see #newBallListener
 * @see #mouseListener
 */
class BallFrame extends JFrame {

    private int ballCount = 1;

    private Ball initBall;

    private ExecutorService runBall = Executors.newFixedThreadPool(20);

    /**
     * The constructor initializes preferred frame properties and initializes a
     * new {@link Ball} object to be later triggered by the {@link #newBallListener},
     * and activated by {@link #addNewBall()}. It then makes the frame visible.
     */
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

    /**
     * The addNewBall method activates the existing ball created in {@link #BallFrame()}
     * upon first click, and afterwords by utilizing {@link #ballCount}, new
     * {@link Ball} objects are created, and then executed. It also limits the user
     * to twenty balls as asked for in the problem statement.
     *
     * @see #BallFrame()
     * @see #ballCount
     * @see Ball
     */
    private void addNewBall() {
        if (ballCount == 1) {
            runBall.execute(initBall);
            ballCount++;
        } else if (ballCount < 20) {
            Ball ball = new Ball();
            add(ball);
            runBall.execute(ball);
            ballCount++;
        } else {
            System.out.println("20 Ball Maximum, Sorry");
        }
    }

    /**
     * a {@link MouseListener} object who's only override is the "mousePressed"
     * function, upon being called triggers {@link #addNewBall()}
     *
     * @see MouseListener
     * @see #addNewBall()
     */
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

    /**
     * The ball class is an extension of the {@link JPanel} that extends the
     * {@link Runnable} interface. It holds a velocity and location for the x and
     * y coordinates relate to it's parent frame, and initializes a color to be used
     * in it's {@link #paintComponent(Graphics)} override. It also stores
     * {@link #VELOCITY_VARIATION} in order to vary it's direction when colliding
     * with a border of the frame.
     *
     * @see JPanel
     * @see Runnable
     * @see #paintComponent(Graphics)
     * @see #VELOCITY_VARIATION
     */
    private class Ball extends JPanel implements Runnable {

        private final int VELOCITY_VARIATION = 2;

        private int vX = -2;
        private int vY = 2;

        private Random rand = new Random();

        private int corX;
        private int corY;

        private Color color = new Color(rand.nextInt(255),
                rand.nextInt(255), rand.nextInt(255));

        /**
         * constructor to set the dimensions of the {@link Ball} to their default
         * size of 80px. It also nullifies their layout in order to use
         * {@link #setBounds(int, int, int, int)} to move the panel across the frame.
         * It's movement is detailed in {@link #run()}, where it's location is updated
         * during thread execution.
         *
         * @see Ball
         * @see #setBounds
         * @see #run()
         */
        Ball() {
            setPreferredSize(new Dimension(80, 80));
            setLayout(null);
            corX = rand.nextInt(500);
            corY = rand.nextInt(500);
            setBounds(corX, corY, 80, 80);
            revalidate();
            repaint();
        }

        /**
         * The method override to draw a new circle with width and height of 75.
         * It also configures antialiasing for smoother movement.
         *
         * @param g Graphics object to be rendered in the {@link JPanel}
         *          constructor.
         */
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.drawOval(0, 0, 75, 75); //draws circle
            g.fillOval(0, 0, 75, 75); //adds color to circle
        }

        /**
         * The run() method required from the {@link Runnable} interface is executed
         * once a thread is started. The function runs continuously and updates the
         * stored corX and corY values before using {@link #setBounds(int, int, int, int)} )}
         * to update the components new location in the frame. corX and corY are
         * advanced by their velocity, unless a collision is detected, which
         * results in a call to {@link #switchX()} or {@link #switchY()} in order
         * to keep the balls in frame at all time, and alter their velocity by
         * a small factor, creating a unique random path.
         *
         * @see Runnable
         * @see #setBounds(int, int, int, int)
         * @see #switchY()
         * @see #switchX()
         */
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

        /**
         * The switchX method flips the velocity in the opposite direction so that
         * {@link Ball} objects move away from border collisions. It also adds a
         * small randomized shift in velocity so that paths are randomized.
         *
         * @see Ball
         * @see #VELOCITY_VARIATION
         */
        private void switchX() {
            vX *= -1;
            Boolean isNeg = (vX < 0);
            int shift = rand.nextInt(VELOCITY_VARIATION);
            vX = (isNeg) ? (vX - shift) : (vX + shift);
            // If velocity in either direction is too large, reset it
            if (vX > 4 || vX < -4)
                vX = (isNeg) ? (-2) : (2);
        }

        /**
         * The switchX method flips the velocity in the opposite direction so that
         * {@link Ball} objects move away from border collisions. It also adds a
         * small randomized shift in velocity so that paths are randomized.
         *
         * @see Ball
         * @see #VELOCITY_VARIATION
         */
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
