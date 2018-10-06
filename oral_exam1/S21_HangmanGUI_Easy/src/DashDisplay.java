import javax.swing.*;
import java.awt.*;

public class DashDisplay extends JPanel {

    Font letterFont = new Font("Courier", Font.BOLD, 45);

    private JLabel first = new JLabel();
    private JLabel second = new JLabel();
    private JLabel third = new JLabel();
    private JLabel wordText = new JLabel();

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

    public void progressUpdate(String progress) {
        first.setText(progress.substring(0, 1));
        second.setText(progress.substring(1, 2));
        third.setText(progress.substring(2));
    }

}
