import javax.swing.*;

/**
 * Launches a new Converter class to enable the user the option to convert from
 * Arabic to Roman and vise versa.
 * <p>
 * Completed as part of assignment S10_ArabicToRomanGUI_Hard
 *
 * @see Converter
 */
public class Launcher {

    /**
     *
     * @param args unused
     * @see Launcher
     */
    public static void main(String args[]){
        Converter window = new Converter();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
