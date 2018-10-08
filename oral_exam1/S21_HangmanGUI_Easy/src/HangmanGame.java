import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.security.SecureRandom;

/**
 * HangmanGame is the main JFrame for the game that shows all progress to the user.
 * It is made up of several labels, a DashDisplay, and two text areas for the user
 * to enter input as guesses to the game. Each game is started with randomized 3
 * letter word, and the user has 6 guesses to attempt to win the game.
 *
 * @see #HangmanGame(String[])
 * @see JFrame
 */
public class HangmanGame extends JFrame {
    private SecureRandom randomGen = new SecureRandom();

    private String goal;
    private String progress = "???";
    private String guesses = "";
    private int guessesLeft = 6;

    private DashDisplay dashDisplay;
    private JTextField wordGuess = new JTextField(3);
    private JTextField letterGuess = new JTextField(1);

    private JLabel guessesRemaining = new JLabel(
            "Guesses Left: " + Integer.toString(guessesLeft));
    private JLabel guessesMade = new JLabel(
            "Guesses Made: " + guesses
    );

    /**
     * The HangmanGame constructor initializes the labels needed to be shown on
     * the screen with proper font and alignment. It also initializes the text areas,
     * and assigns them an actionListener defined below, and a key listener, sometimes
     * referred to as a "chomp" to remove any extra letters past their preset width.
     * An instance of DashDisplay is added to the JFrame to display the user's progress.
     * These elements are then all added to the JFrame with proper spacing in-between.
     *
     * @param words the three letter word for attempting to guess.
     * @see DashDisplay
     * @see letterGuessActionListener
     * @see wordGuessActionListener
     */
    public HangmanGame(String[] words) {
        super("Hangman GUI");
        setSize(600, 550);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        goal = words[randomGen.nextInt(words.length)];
        System.out.println(goal);

        JLabel title = new JLabel("Ben's Hangman Game");
        title.setFont(new Font("Courier", Font.ITALIC, 26));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        dashDisplay = new DashDisplay();

        guessesRemaining.setFont(new Font("Courier", Font.BOLD, 30));
        guessesRemaining.setAlignmentX(Component.CENTER_ALIGNMENT);

        guessesMade.setFont(new Font("Courier", Font.BOLD, 30));
        guessesMade.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel letterGuessLabel = new JLabel("Guess a Letter:");
        letterGuessLabel.setFont(new Font("Courier", Font.ITALIC, 20));
        letterGuessLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        letterGuess.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (letterGuess.getText().length() == 1) {
                    e.consume();
                }
            }
        });
        letterGuess.setAlignmentX(Component.CENTER_ALIGNMENT);
        letterGuess.setMaximumSize(new Dimension(100, 25));
        letterGuess.addActionListener(new letterGuessActionListener());

        JLabel wordGuessLabel = new JLabel("Guess a word:");
        wordGuessLabel.setFont(new Font("Courier", Font.ITALIC, 20));
        wordGuessLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        wordGuess.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (wordGuess.getText().length() == 3) {
                    e.consume();
                }
            }
        });
        wordGuess.setAlignmentX(Component.CENTER_ALIGNMENT);
        wordGuess.setMaximumSize(new Dimension(100, 25));
        wordGuess.addActionListener(new wordGuessActionListener());

        setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        add(Box.createRigidArea(new Dimension(0, 15)));
        add(title);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(dashDisplay);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(guessesRemaining);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(guessesMade);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(wordGuessLabel);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(wordGuess);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(letterGuessLabel);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(letterGuess);

    }

    /**
     * Removes a guess from how many the user has left and notifies them of their
     * incorrect guess. If guesses hit zero, the function notifies the user that
     * they have lost, and calls endGame() to end the game.
     *
     * @see #endGame()
     */
    public void badGuess() {
        guessesLeft--;
        guessesRemaining.setText("Guesses Left: " + Integer.toString(guessesLeft));
        JOptionPane.showMessageDialog
                (null, "You Guessed Incorrectly :(",
                        "Nope", JOptionPane.PLAIN_MESSAGE);
        letterGuess.setText("");
        wordGuess.setText("");
        if (guessesLeft == 0) {
            JOptionPane.showMessageDialog
                    (null, "You're out of guesses :(",
                            "Dummy", JOptionPane.PLAIN_MESSAGE);
            endGame();
        }
    }

    /**
     * Notifies the user that they have made a correct guess, and if their guess
     * happened to complete the word, notifies them of their win, and calls endGame().
     *
     * @see #endGame()
     */
    public void goodGuess() {
        JOptionPane.showMessageDialog
                (null, "Nice Guess!",
                        "Good Job", JOptionPane.PLAIN_MESSAGE);
        if (progress.equals(goal)) {
            JOptionPane.showMessageDialog
                    (null, "You Win!",
                            "Hooray!", JOptionPane.PLAIN_MESSAGE);
            endGame();
        }
    }

    /**
     * Deactivates the two text areas for guessing, and reflects the complete
     * word within the dashDisplay by making a call to it's method progressUpdate().
     *
     * @see DashDisplay
     */
    public void endGame() {
        wordGuess.setEnabled(false);
        letterGuess.setEnabled(false);
        dashDisplay.progressUpdate(goal);
    }

    /**
     * An action listener dedicated to the letterGuess text area field. It compares
     * the letter entered to each letter in the goal string, and if a match is found,
     * updates the users progress, makes the appropriate call to either goodGuess()
     * or badGuess(), and updates the DashDisplay appropriately.
     *
     * @see DashDisplay
     * @see #goodGuess()
     * @see #badGuess()
     * @see ActionListener
     */
    class letterGuessActionListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            JTextField source = (JTextField) e.getSource();
            String letterEntry = source.getText();
            boolean match = false;
            if (goal.substring(0, 1).equals(letterEntry)) {
                progress = letterEntry + progress.substring(1);
                match = true;
            }
            if (goal.substring(1, 2).equals(letterEntry)) {
                progress = progress.substring(0, 1) + letterEntry +
                        progress.substring(2);
                match = true;
            }
            if (goal.substring(2).equals(letterEntry)) {
                progress = progress.substring(0, 2) + letterEntry;
                match = true;
            }
            dashDisplay.progressUpdate(progress);
            if (!match) {
                badGuess();
                guesses += letterEntry;
                guessesMade.setText("Guesses Made: " + guesses);
            } else {
                goodGuess();
            }
            letterGuess.setText("");
            wordGuess.setText("");
        }
    }

    /**
     * The wordGuessActionListener is an ActionListener dedicated to the wordGuess
     * text area. Upon being called through the event handler with a press of the
     * enter key on the area, the listener compares the text in wordGuess to the
     * goal text. If the text is a match, goodGuess() is called therefore ending
     * the game. If the text is not a match, badGuess() is called.
     *
     * @see #goodGuess()
     * @see #badGuess()
     * @see ActionListener
     */
    class wordGuessActionListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if (!wordGuess.getText().equals(goal)) {
                badGuess();
            } else {
                JOptionPane.showMessageDialog
                        (null, "You Guessed Correctly! You Win!",
                                "Hooray!", JOptionPane.PLAIN_MESSAGE);
                endGame();
            }
            wordGuess.setText("");
            letterGuess.setText("");
        }

    }
}
