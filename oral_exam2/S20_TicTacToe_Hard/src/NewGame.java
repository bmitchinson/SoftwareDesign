import java.util.Scanner;

/**
 * A class who's only purpose is to create a new game of tic tac toe,
 * by asking the user which of three modes they'll like to play, based on which
 * subclasses of Player will be participating.
 * <p>
 * Completed as part of the S20_TicTacToe_Hard assignment
 *
 * @see Player
 * @see Board
 * @see HumanPlayer
 * @see ComputerPlayer
 */
public class NewGame {
    /**
     *
     * @param args - unused
     * @see NewGame
     */
    public static void main(String args[]){
        Scanner input = new Scanner(System.in);

        System.out.println("******************************************\n" +
                           "*          TIC-TAC-TOE LAUNCHER          *\n" +
                           "******************************************\n");
        System.out.println("Game Modes:");
        System.out.println("1) Human vs. Human");
        System.out.println("2) Human vs. Computer");
        System.out.println("3) Computer vs. Computer");
        System.out.print("Choice: ");
        while (!input.hasNextInt()){
            input.nextLine();
            System.out.print("Please enter 1 2 or 3 (Nice try):");
        }
        int modeSelect = input.nextInt();
        input.nextLine();

        Player playerOne, playerTwo;

        switch (modeSelect){
            case (1):
                playerOne = new HumanPlayer('X');
                playerTwo = new HumanPlayer('O');
                break;
            case (2):
                playerOne = new HumanPlayer('X');
                playerTwo = new ComputerPlayer('O');
                break;
            case (3):
                playerOne = new ComputerPlayer('X');
                playerTwo = new ComputerPlayer('O');
                break;

            default:
                System.out.println("That wasn't an option, so we'll go with 3");
                playerOne = new ComputerPlayer('X');
                playerTwo = new ComputerPlayer('O');
        }

        new Board(playerOne, playerTwo);
        System.out.println("******************************************");
        System.out.println("*         Thank you for playing!         *");
        System.out.println("******************************************");
    }
}
