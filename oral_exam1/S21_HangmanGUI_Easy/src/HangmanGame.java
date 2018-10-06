import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.security.SecureRandom;

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

    public HangmanGame(String[] words) {
        super("Hangman GUI");
        setSize(600,600);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        goal = words[randomGen.nextInt(words.length)];

        JLabel title = new JLabel("Ben's Hangman Game");
        title.setFont(new Font("Courier", Font.ITALIC, 26));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        dashDisplay = new DashDisplay();

        guessesRemaining.setFont(new Font("Courier", Font.BOLD, 30));
        guessesRemaining.setAlignmentX(Component.CENTER_ALIGNMENT);

        guessesMade.setFont(new Font("Courier", Font.BOLD, 30));
        guessesMade.setAlignmentX(Component.CENTER_ALIGNMENT);

        letterGuess.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (letterGuess.getText().length() == 1) {
                    e.consume();
                }
            }
        });
        letterGuess.setAlignmentX(Component.CENTER_ALIGNMENT);
        letterGuess.setMaximumSize(new Dimension(100,25));
        letterGuess.addActionListener(new letterGuessActionListener(this));

        wordGuess.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (wordGuess.getText().length() == 3) {
                    e.consume();
                }
            }
        });
        wordGuess.setAlignmentX(Component.CENTER_ALIGNMENT);
        wordGuess.setMaximumSize(new Dimension(100,25));
        wordGuess.addActionListener( new wordGuessActionListener(this));

        setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        add(Box.createRigidArea(new Dimension(0,15)));
        add(title);
        add(Box.createRigidArea(new Dimension(0,20)));
        add(dashDisplay);
        add(Box.createRigidArea(new Dimension(0,20)));
        add(guessesRemaining);
        add(Box.createRigidArea(new Dimension(0,20)));
        add(guessesMade);
        add(Box.createRigidArea(new Dimension(0,20)));
        add(wordGuessLabel);
        add(Box.createRigidArea(new Dimension(0,20)));
        add(wordGuess);
        add(Box.createRigidArea(new Dimension(0,20)));
        add(letterGuessLabel);
        add(Box.createRigidArea(new Dimension(0,20)));
        add(letterGuess);

    }

    class letterGuessActionListener implements ActionListener {
        private HangmanGame game;

        public letterGuessActionListener(HangmanGame game){
            this.game = game;
        }

        public void actionPerformed(ActionEvent e){
            JTextField source = (JTextField) e.getSource();
            String letterEntry = source.getText();
            System.out.println("Letter:"+letterEntry);
        }
    }

    class wordGuessActionListener implements ActionListener {
        private HangmanGame game;

        public wordGuessActionListener(HangmanGame game) {
            this.game = game;
        }
        public void actionPerformed(ActionEvent e){
            JTextField source = (JTextField) e.getSource();
            String wordEntry = source.getText();
            System.out.println("Word:"+wordEntry);
        }

    }
}
