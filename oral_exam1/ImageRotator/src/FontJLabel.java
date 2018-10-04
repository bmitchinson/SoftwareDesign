import javax.swing.*;
import java.awt.*;

public class FontJLabel extends JLabel {

    public FontJLabel(Font font, String title, int align){
        super(title, align);
        this.setFont(font);
        setAlignmentX(Component.CENTER_ALIGNMENT);
    }

}
