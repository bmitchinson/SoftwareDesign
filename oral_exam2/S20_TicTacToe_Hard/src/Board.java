import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Class that extends JFrame to represent a tic tac toe board. Initializes a
 * series of disabled buttons that serve as the cross board, and hold methods to
 * mark player inputs on the board, as well as interpret board state to check for
 * a game over and control gameplay. Can be interacted with by any Player class,
 * regardless if Human or Computer extensions.
 * <p>
 * Completed as part of the S20_TicTacToe_Hard assignment
 *
 * @see Player
 * @see HumanPlayer
 * @see ComputerPlayer
 * @see JFrame
 * @see #Board(Player, Player)
 */
public class Board extends JFrame {

    // GUI Elements
    private JTextArea log = new JTextArea();
    private JScrollPane logPane = new JScrollPane();
    public JButton[] buttons = new JButton[9];
    private JPanel buttonPanel = new JPanel();

    // Gameplay Variables
    private String endResult;
    final public String blank = "(   )";

    /**
     *
     * A constructor for the board that stores references to the players to
     * validate their input, and place the required GUI elements on screen. After
     * visual layouts are arranged, the board enters a gameplay loop determined by
     * {@link #isGameOver()}
     *
     * @param playerOne a reference to the first player object
     * @param playerTwo a reference to the second player object
     * @see Player
     * @see HumanPlayer
     * @see ComputerPlayer
     *
     */
    public Board(Player playerOne, Player playerTwo) {

        // All GUI Initial Configuration
        setTitle("bmitchinson's Tic-Tac-Toe");
        log.setEditable(false);
        log.setLineWrap(true);
        log.setPreferredSize(new Dimension(390, 50));
        logPane.setViewportView(log);
        logPane.setPreferredSize(new Dimension(390, 50));

        buttonPanel.setPreferredSize(new Dimension(390, 390));
        buttonPanel.setLayout(new GridLayout(3, 3));

        // Give players access to board:
        playerOne.giveBoard(this);
        playerTwo.giveBoard(this);

        for (int i = 0; i < 9; i++) {
            buttons[i] = new JButton(blank);
            buttons[i].setFont(new Font("Veranda", 1, 45));
            buttons[i].setName(String.valueOf(i));
            buttons[i].setFocusPainted(false);
            buttons[i].setBackground(Color.white);
            buttons[i].setEnabled(false);
            buttonPanel.add(buttons[i]);
        }
        add(logPane, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(390, 480);
        setResizable(false);
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);
        setVisible(true);

        log.setText("Starting new game\n");
        // Let players take alternating turns until the game is over
        while (!isGameOver()){
            playerOne.makeMove();
            if (!isGameOver()){
                playerTwo.makeMove();
            }
        }

        print(endResult);
    }

    /**
     * Print a message to the log above the tictactoe board
     * @param message message to be displayed
     */
    private void print(String message) {
        log.append(message + "\n");
    }

    /**
     * Mark a slot on the board and alter it's text if allowed by game rules.
     * If allowed and mark is made, return true, if not, return false.
     * @param slot slot requested to alter
     * @param symbol symbol to be marked if allowed
     * @return result of successful mark
     */
    public boolean mark(int slot, char symbol) {
        if(!buttons[slot].getText().equals(blank)){
            print("Slot " + slot + " is already filled. Try again.");
            return false;
        }
        else {
            buttons[slot].setText(String.valueOf(symbol));
            return true;
        }
    }

    /**
     * Check the 10 possible scenarios in which a game of tictactoe could end,
     * detailed in comments within the method.
     *
     * @return if game is over based on board state
     */
    private boolean isGameOver() {
        boolean gameover = false;
        boolean fullboard = true;

        String winningSymbol;
        // Get the values of all buttons for comparison of win conditions
        String[] marks = {buttons[0].getText(), buttons[1].getText(),
                buttons[2].getText(), buttons[3].getText(),
                buttons[4].getText(), buttons[5].getText(),
                buttons[6].getText(), buttons[7].getText(),
                buttons[8].getText()};

        // Check all 8 possible win conditions
        // (0,1,2) (3,4,5) (6,7,8) (Rows)
        for (int i = 0; i < 9; i += 3) {
            if (!gameover && !marks[i].equals(blank) &&
                    marks[i].equals(marks[1 + i]) &&
                    marks[1 + i].equals(marks[2 + i])) {
                gameover = true;
                this.endResult = "Player: " + marks[i] + " wins!";
            }
        }
        // (0,3,6) (1,4,7) (2,5,8) (Columns)
        for (int i = 0; i < 3; i++) {
            if (!gameover && !marks[i].equals(blank) &&
                    marks[i].equals(marks[i + 3]) &&
                    marks[i + 3].equals(marks[i + 6])) {
                gameover = true;
                this.endResult = "Player: " + marks[i] + " wins!";
            }
        }
        //(0,4,8)
        if (!gameover && !marks[4].equals(blank) &&
                marks[0].equals(marks[4]) &&
                marks[4].equals(marks[8])){
            gameover = true;
            this.endResult = "Player: " + marks[0] + " wins!";
        }
        //(2,4,6)
        else if (!marks[4].equals(blank) &&
                marks[2].equals(marks[4]) &&
                marks[4].equals(marks[6])){
            gameover = true;
            this.endResult = "Player: " + marks[4] + " wins!";
        }

        // Unless a player has already won, check to make sure the board
        // isn't full. (Tie game case)
        for (int i=0; i<9; i++){
            if (marks[i].equals(blank)){
                fullboard = false;
                i = 9;
            }
        }
        if (fullboard){
            gameover = true;
            endResult = "Tie game!";
        }

        return gameover;
    }

}
