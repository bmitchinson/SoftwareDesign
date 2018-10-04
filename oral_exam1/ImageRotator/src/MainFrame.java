import javax.swing.*;
import java.awt.*;

/**
 * The MainFrame class is an extension of the JFrame object that instantiates
 * an ImageComponent and Controls object to added to the main display frame.
 *
 * @see JFrame
 * @see #MainFrame()
 * @see ImageComponent
 * @see Controls
 */
public class MainFrame extends JFrame {

    private ImageComponent imageComponent = new ImageComponent("/img/h.png");
    private Controls controls = new Controls(imageComponent);

    /**
     * The MainFrame constructor sets the window title, fixed resolution,
     * layout, and default close operation. It's ImageComponent and Controls
     * objects are then added to the frame.
     *
     * @see ImageComponent
     * @see Controls
     */
    public MainFrame() {
        super("Image Rotator");
        setSize(700, 360);
        setResizable(false);
        setLayout(new FlowLayout(FlowLayout.LEADING, 15, 15));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        add(imageComponent);
        add(controls);
    }

}


