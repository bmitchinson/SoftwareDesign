import java.util.Scanner;

/**
 * An extension of the Player class that is able to make a move based on player
 * input alone.
 *
 * @see Player
 */
public class HumanPlayer extends Player {

    private Scanner input = new Scanner(System.in);

    /**
     * Initalize a Player object by passing the player symbol
     *
     * @param symbol mark player uses in tic tac toe
     * @see Player
     */
    public HumanPlayer(char symbol) {
        super(symbol);
    }

    /**
     * Ask the user where they would like to place their symbol on the board,
     * validate their input to make sure the move is possible, and don't end
     * until they've made a successful mark.
     *
     * @see Board
     * @see Player
     */
    public void makeMove() {
        System.out.println("******************************************");
        System.out.print("Player " + String.valueOf(symbol) + ": " +
                "Make a selection of 0-8: ");
        boolean markMade = false;
        // Validate input so that it's an int 0-8 + move is successful
        while (!markMade) {
            while (!input.hasNextInt()) {
                input.nextLine();
                System.out.print("Please enter a selection of 0-8:");
            }
            int slot = input.nextInt();
            input.nextLine();
            if (8 < slot || slot < 0){
                System.out.print("Enter a number between 0 and 8:");
            }
            else {
                markMade = board.mark(slot, symbol);
            }
        }
    }
}
