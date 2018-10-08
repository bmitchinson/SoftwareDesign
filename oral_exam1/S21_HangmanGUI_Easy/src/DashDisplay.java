import javax.swing.*;
import java.awt.*;

/**
 * Dash Display is a JPanel that shows the user current progress made towards
 * guessing the goal word.
 *
 * @see #DashDisplay()
 */
public class DashDisplay extends JPanel {

    Font letterFont = new Font("Courier", Font.BOLD, 45);

    private JLabel first = new JLabel();
    private JLabel second = new JLabel();
    private JLabel third = new JLabel();
    private JLabel wordText = new JLabel();

    /**
     * DashDisplay is initialized to hold a title label, as well as three separate
     * labels, each of which representing a character in the word to guess. They
     * are initially all set to display only a question mark since the game starts
     * with no letters guessed.
     *
     * @see #progressUpdate(String)
     */
    public DashDisplay() {
        super(new FlowLayout(FlowLayout.CENTER, 40, 0));

        setMaximumSize(new Dimension(400, 120));

        first.setFont(letterFont);
        second.setFont(letterFont);
        third.setFont(letterFont);
        progressUpdate("???");

        wordText.setFont(letterFont);
        wordText.setText("Mystery Word:");

        add(wordText);
        add(first);
        add(second);
        add(third);
    }

    /**
     * progressUpdate sets each of the 3 labels representing characters of user
     * progress towards guessing the three letter word.
     *
     * @param progress a string to be represented character by character with
     *                 three labels.
     */
    public void progressUpdate(String progress) {
        first.setText(progress.substring(0, 1));
        second.setText(progress.substring(1, 2));
        third.setText(progress.substring(2));
    }

}
