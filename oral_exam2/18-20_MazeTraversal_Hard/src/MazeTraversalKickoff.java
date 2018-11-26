import java.util.Scanner;

/**
 * MazeTraversalKickoff stores three various mazes (12 x 12 character grids)
 * for recursive traversal, prompts the user for which one they would like to
 * solve, and creates a new {@link MazeTraversal} instance for navigation.
 * The {@link MazeTraversal} class handles the rest within it's constructor.
 * <p>
 * It is a class designed to present the solution to 18-20_MazeTraversal_Hard
 *
 * @author Ben Mitchinson
 * @see MazeTraversal
 */
public class MazeTraversalKickoff {
    /**
     * Driver method for {@link MazeTraversal}
     * @see MazeTraversal
     * @param args - unused
     */
    public static void main(String args[]) {
        char[][] mazeOne = {
                {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#'},
                {'#', '.', '.', '.', '#', '.', '.', '.', '.', '.', '.', '#'},
                {'.', '.', '#', '.', '#', '.', '#', '#', '#', '#', '.', '#'},
                {'#', '#', '#', '.', '#', '.', '.', '.', '.', '#', '.', '#'},
                {'#', '.', '.', '.', '.', '#', '#', '#', '.', '#', '.', '.'},
                {'#', '#', '#', '#', '.', '#', '.', '#', '.', '#', '.', '#'},
                {'#', '.', '.', '#', '.', '#', '.', '#', '.', '#', '.', '#'},
                {'#', '#', '.', '#', '.', '#', '.', '#', '.', '#', '.', '#'},
                {'#', '.', '.', '.', '.', '.', '.', '.', '.', '#', '.', '#'},
                {'#', '#', '#', '#', '#', '#', '.', '#', '#', '#', '.', '#'},
                {'#', '.', '.', '.', '.', '.', '.', '#', '.', '.', '.', '#'},
                {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#'}};

        char[][] mazeTwo = {
                {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#'},
                {'#', '.', '#', '.', '.', '#', '.', '.', '.', '.', '.', '#'},
                {'#', '.', '#', '#', '.', '.', '.', '#', '.', '#', '#', '#'},
                {'#', '.', '#', '#', '#', '.', '#', '#', '.', '.', '.', '#'},
                {'#', '.', '.', '.', '#', '.', '.', '#', '.', '#', '#', '#'},
                {'#', '.', '#', '.', '#', '#', '.', '#', '.', '#', '#', '#'},
                {'#', '.', '#', '.', '.', '#', '.', '#', '.', '.', '.', '#'},
                {'#', '.', '#', '#', '.', '#', '.', '#', '#', '.', '#', '#'},
                {'#', '.', '.', '#', '.', '#', '.', '#', '.', '.', '#', '#'},
                {'#', '#', '.', '#', '.', '#', '.', '#', '.', '#', '#', '#'},
                {'.', '.', '.', '#', '.', '.', '.', '#', '.', '.', '.', '.'},
                {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#'}};

        char[][] mazeThree = {
                {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#'},
                {'#', '.', '.', '.', '.', '#', '.', '.', '.', '#', '.', '.'},
                {'#', '.', '#', '#', '.', '#', '#', '#', '.', '#', '.', '#'},
                {'#', '.', '#', '.', '.', '#', '.', '.', '.', '#', '.', '#'},
                {'#', '.', '#', '.', '#', '#', '.', '#', '.', '#', '.', '#'},
                {'#', '.', '#', '.', '#', '.', '.', '#', '.', '.', '.', '#'},
                {'#', '.', '#', '.', '#', '#', '.', '#', '#', '.', '#', '#'},
                {'.', '.', '#', '.', '.', '#', '.', '#', '.', '.', '#', '#'},
                {'#', '#', '#', '#', '.', '#', '.', '#', '.', '#', '.', '#'},
                {'#', '.', '.', '#', '.', '#', '.', '#', '.', '#', '.', '#'},
                {'#', '#', '.', '.', '.', '.', '.', '#', '.', '.', '.', '#'},
                {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#'}};

        Scanner input = new Scanner(System.in);

        System.out.println("******************************************\n" +
                "*               MAZE SOLVER              *\n" +
                "******************************************\n");
        System.out.print("Would you like to solve maze 1, 2, or 3:");
        while (!input.hasNextInt()) {
            input.nextLine();
            System.out.print("Please enter 1 2 or 3 (Nice try):");
        }
        int mazeSelection = input.nextInt();
        input.nextLine();

        switch (mazeSelection) {
            case (1):
                new MazeTraversal(mazeOne, 350);
                break;
            case (2):
                new MazeTraversal(mazeTwo, 350);
                break;
            case (3):
                new MazeTraversal(mazeThree, 350);
                break;

            default:
                System.out.println("That wasn't an option, so we'll go with 1");
                new MazeTraversal(mazeOne, 350);
        }
    }
}
