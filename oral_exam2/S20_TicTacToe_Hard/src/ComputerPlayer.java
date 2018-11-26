import javax.swing.*;

/**
 * An extension of the Player class that is able to make a move based determining
 * Board state with no user input.
 *
 * @see Player
 */
public class ComputerPlayer extends Player {

    /**
     * Initialize a Player object by passing the player symbol
     *
     * @param symbol mark player uses in tic tac toe
     * @see Player
     */
    public ComputerPlayer(char symbol){
        super(symbol);
    }

    /**
     * Iterate through the buttons in place on the Board object, and see if they're
     * available to make a mark. If possible, make it.
     *
     * @see Board
     * @see Player
     */
    public void makeMove(){
        boolean markMade = false;
        // A delay so the user can see the mark that the computer made
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("Computer couldn't sleep");
            e.printStackTrace();
        }
        for (JButton b : board.buttons){
            if (!markMade && b.getText().equals(board.blank)){
                markMade = board.mark(Integer.valueOf(b.getName()), symbol);
            }
        }
    }

}
