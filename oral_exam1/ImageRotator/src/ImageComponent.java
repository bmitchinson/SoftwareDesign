import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.awt.geom.AffineTransform;

public class ImageComponent extends JPanel {
    BufferedImage photo;
    AffineTransform at = new AffineTransform();
    double angle = 0;

    public ImageComponent(String url) throws IOException {
        photo = ImageIO.read(ImageComponent.class.getResource
                (url));
        setBorder(BorderFactory.createLineBorder(Color.black));
        setPreferredSize(new Dimension(300, 300));
    }

    @Override
    protected void paintComponent(Graphics g){
        Graphics2D g2d = (Graphics2D)g;
        super.paintComponent(g2d);
        g2d.drawImage(photo, at, this);
    }

    protected void rotate(double angle){
        this.angle = angle;
        at = at.getRotateInstance(Math.toRadians(this.angle),150,150);
        repaint();
    }

    public void addAngle(double addAngle) {
        rotate(this.angle + addAngle);
    }

}
