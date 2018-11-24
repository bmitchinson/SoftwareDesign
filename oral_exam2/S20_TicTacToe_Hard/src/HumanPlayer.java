import java.util.Scanner;

public class HumanPlayer extends Player {

    private Scanner input = new Scanner(System.in);

    public HumanPlayer(char symbol) {
        super(symbol);
    }

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
