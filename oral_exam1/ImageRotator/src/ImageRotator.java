/**
 * The Image Rotator program displays a single frame with an image view on the
 * right side, and controls to manipulate that image on the right. Rotation's
 * are performed continuously with adjusting speed, as well as setting a
 * preferred angle.
 * <p>
 * It is an implementation of the assignment: S22_ImageRotator_Hard
 *
 * @Author Ben Mitchinson
 * @see #main(String[])
 * @since 10/3/2018
 */
public class ImageRotator {

    /**
     * The main method for the class instantiates a new MainFrame object, and
     * sets the frame to be visible.
     *
     * @param args Unused
     * @see MainFrame
     */
    public static void main(String[] args) {
        MainFrame mainFrame = new MainFrame();
        mainFrame.setVisible(true);
    }
}
