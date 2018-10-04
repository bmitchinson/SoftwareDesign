import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class MainFrame extends JFrame{

    private ImageComponent imageComponent = new ImageComponent("/img/r.png");
    private Controls controls = new Controls(imageComponent);

    public MainFrame() throws IOException {
        super("Image Rotator");
        setSize(700,360);
        setResizable(false);
        setLayout(new FlowLayout(FlowLayout.LEADING,15,15));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        add(imageComponent);
        add(controls);
    }

}

