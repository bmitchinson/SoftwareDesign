import javax.swing.*;
import java.awt.*;

/**
 * An extension of the JLabel class created with the sole purpose of having a
 * constructor function to assign font objects upon initialization.
 *
 * @see JLabel
 * @see Font
 */
public class FontJLabel extends JLabel {

    /**
     * A constructor function to assign font objects upon initialization of
     * JLabels
     *
     * @param font  a Font object to use to setFont
     * @param title a String the include in the title assignment for the JLabel
     *              constructor.
     * @param align a integer value to be passed along to the JLabel constructor
     *              for alignment.
     * @see JLabel
     * @see Font
     */
    public FontJLabel(Font font, String title, int align) {
        super(title, align);
        this.setFont(font);
        setAlignmentX(Component.CENTER_ALIGNMENT);
    }

}
