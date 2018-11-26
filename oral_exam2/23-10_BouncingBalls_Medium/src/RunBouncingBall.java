import javax.swing.*;

/**
 * The class RunBouncingBall's sole purpose is to create a new {@link BallFrame}
 * object for viewing.
 * <p>
 * It is a completion of the 23-10_BouncingBalls_Medium assignment
 * <p>
 * It was completed in place of 23-10_BouncingBalls_Easy for extra credit
 *
 * @author Ben Mitchinson
 * @see BallFrame
 */
public class RunBouncingBall{

    public static void main(String args[]) {
        BallFrame frame = new BallFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
