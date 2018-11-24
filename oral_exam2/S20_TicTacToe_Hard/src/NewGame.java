import java.util.Scanner;

public class NewGame {
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
