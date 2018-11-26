import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.DefaultEditorKit;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

/**
 * The Converter class is an extension of a JFrame that allows for a user to
 * convert from Arabic to Roman
 */
public class Converter extends JFrame {

    // Conversion Table
    HashMap<Character, Integer> table = new HashMap<Character, Integer>();

    // GUI Elements
    private JLabel romanLabel = new JLabel("Roman:");
    private JLabel arabicLabel = new JLabel("Arabic: ");
    private JTextField romanText = new JTextField(20);
    private JTextField arabicText = new JTextField(20);
    private JPanel romanPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
    private JPanel arabicPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));

    public Converter() {
        setTitle("Roman Convert");
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setResizable(false);
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);

        romanLabel.setFont(new Font("Veranda", Font.PLAIN, 14));
        romanText.setFont(new Font("Veranda", Font.PLAIN, 14));
        romanText.getDocument().addDocumentListener(romanListener);

        //romanText.addKeyListener(romanKeyListener);

        romanPanel.add(romanLabel);
        romanPanel.add(romanText);

        arabicLabel.setFont(new Font("Veranda", Font.PLAIN, 14));
        arabicText.setFont(new Font("Veranda", Font.PLAIN, 14));
        arabicText.getDocument().addDocumentListener(arabicListener);
        //arabicText.addKeyListener(arabicKeyListener);

        arabicPanel.add(arabicLabel);
        arabicPanel.add(arabicText);

        JLabel title = new JLabel("  Roman Converter");
        title.setFont(new Font("Veranda", Font.BOLD, 23));

        setSize(new Dimension(300, 140));

        add(title);
        add(romanPanel);
        add(arabicPanel);

        initalizeRomanTable();
        setVisible(true);

    }

    // https://tinyurl.com/BenSWDSource1
    private String intToRoman(int num) {
        if (num < 0 || num > 3999) {
            return "Enter a # in range 1-3999";
        }
        String m[] = {"", "M", "MM", "MMM"};
        String c[] = {"", "C", "CC", "CCC", "CD", "D",
                "DC", "DCC", "DCCC", "CM"};
        String x[] = {"", "X", "XX", "XXX", "XL", "L",
                "LX", "LXX", "LXXX", "XC"};
        String i[] = {"", "I", "II", "III", "IV", "V",
                "VI", "VII", "VIII", "IX"};

        // Converting to roman
        String thousands = m[num / 1000];
        String hundreds = c[(num % 1000) / 100];
        String tens = x[(num % 100) / 10];
        String ones = i[num % 10];

        String ans = thousands + hundreds + tens + ones;

        return ans;
    }

    // https://tinyurl.com/BenSWDSource2
    private int romanToInt(String roman) {
        int intNum = 0;
        int prev = 0;
        int temp = 0;
        for (int i = roman.length() - 1; i >= 0; i--) {
            try {
                temp = table.get(Character.toUpperCase(roman.charAt(i)));
                if (temp < prev)
                    intNum -= temp;
                else
                    intNum += temp;
                prev = temp;
            } catch (NullPointerException e) {
                return -1;
            }
        }
        return intNum;
    }

    private void initalizeRomanTable() {
        table.put('I', 1);
        table.put('X', 10);
        table.put('C', 100);
        table.put('M', 1000);
        table.put('V', 5);
        table.put('L', 50);
        table.put('D', 500);
    }

    DocumentListener romanListener = new DocumentListener() {

        @Override
        public void insertUpdate(DocumentEvent e) {
            // Temporarily remove the listener from the other field
            //     so that once converted text is inserted, the other field
            //     doesn't re-attempt conversion unnecessarily.
            arabicText.getDocument().removeDocumentListener(arabicListener);
            String roman = romanText.getText();
            int result = romanToInt(roman);

            if (result == -1)
                arabicText.setText("Invalid roman entry");
            else
                arabicText.setText(String.valueOf(result));

            arabicText.getDocument().addDocumentListener(arabicListener);
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            // Temporarily remove the listener from the other field
            //     so that once converted text is inserted, the other field
            //     doesn't re-attempt conversion unnecessarily.
            arabicText.getDocument().removeDocumentListener(arabicListener);
            String roman = romanText.getText();
            int result = romanToInt(roman);

            if (result == -1)
                arabicText.setText("Invalid roman entry");
            else
                arabicText.setText(String.valueOf(result));

            arabicText.getDocument().addDocumentListener(arabicListener);
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
        }
    };

    DocumentListener arabicListener = new DocumentListener() {

        @Override
        public void insertUpdate(DocumentEvent e) {
            // Temporarily remove the listener from the other field
            //     so that once converted text is inserted, the other field
            //     doesn't re-attempt conversion unnecessarily.
            romanText.getDocument().removeDocumentListener(romanListener);

            int arabic = 0;
            try {
                arabic = Integer.valueOf(arabicText.getText());
                romanText.setText(intToRoman(arabic));
            } catch (NumberFormatException error) {
                if (arabicText.getText().equals("")) {
                    romanText.setText("");
                } else {
                    romanText.setText("Invalid arabic entry");
                }
            }
            romanText.getDocument().addDocumentListener(romanListener);
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            // Temporarily remove the listener from the other field
            //     so that once converted text is inserted, the other field
            //     doesn't re-attempt conversion unnecessarily.
            romanText.getDocument().removeDocumentListener(romanListener);

            int arabic = 0;
            try {
                arabic = Integer.valueOf(arabicText.getText());
                romanText.setText(intToRoman(arabic));
            } catch (NumberFormatException error) {
                if (arabicText.getText().equals("")) {
                    romanText.setText("");
                } else {
                    romanText.setText("Invalid arabic entry");
                }
            }
            romanText.getDocument().addDocumentListener(romanListener);
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
        }
    };

}
