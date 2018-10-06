import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.awt.geom.AffineTransform;

/**
 * The ImageComponent class is an extension of the JPanel class that reads in
 * an Image, then applies an AffineTransform object to alter it as a Graphics2D
 * object, to be painted on the panel.
 *
 * @see JPanel
 * @see AffineTransform
 * @see Graphics2D
 * @see BufferedImage
 * @see #ImageComponent(String)
 */
public class ImageComponent extends JPanel {
    private BufferedImage photo;
    private AffineTransform at = new AffineTransform();
    private double angle = 45;

    /**
     * The ImageComponent constructor reads in an image using the provided uri,
     * then sets a border around the panel and sets a fixed dimension.
     *
     * @param url Relative image file address, hardcoded in MainFrame to be
     *            "h.png" from the included img package.
     */
    public ImageComponent(String url) {
        try {
            photo = ImageIO.read(ImageComponent.class.getResource(url));
        } catch (IOException e) {
            e.printStackTrace();
        }

        setBorder(BorderFactory.createLineBorder(Color.black));
        setPreferredSize(new Dimension(300, 300));
    }

    /**
     * The override of the panel paintComponent method paints a graphic, but
     * one that is first casted to Graphics2D so that is can be provided an
     * AffineTransform object for rotation and transformation.
     *
     * @param g the graphics object to be casted and transformed
     * @see AffineTransform
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        super.paintComponent(g2d);
        g2d.drawImage(photo, at, this);
    }

    /**
     * The rotate method updates the local AffineTransform object to represent
     * the provided angle as a radian.
     *
     * @param angle the desired angle of rotation for the image
     * @see AffineTransform
     */
    public void rotate(double angle) {
        this.angle = angle;
        at = at.getRotateInstance(Math.toRadians(this.angle), 150, 150);
        repaint();
    }

    /**
     * addAngle adds it's angle parameter to the current image angle for
     * the timer handler in the Controls class to properly increment the rotation
     * of the image.
     *
     * @param addAngle
     * @see #rotate(double)
     * @see Controls
     */
    public void addAngle(double addAngle) {
        rotate(this.angle + addAngle);
    }

}
