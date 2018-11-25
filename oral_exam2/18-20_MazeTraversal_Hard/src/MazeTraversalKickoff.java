import java.util.Scanner;

public class MazeTraversalKickoff {
    public static void main(String args[]) throws InterruptedException {
        char[][] mazeOne = {
                {'#','#','#','#','#','#','#','#','#','#','#','#'},
                {'#','.','.','.','#','.','.','.','.','.','.','#'},
                {'.','.','#','.','#','.','#','#','#','#','.','#'},
                {'#','#','#','.','#','.','.','.','.','#','.','#'},
                {'#','.','.','.','.','#','#','#','.','#','.','.'},
                {'#','#','#','#','.','#','.','#','.','#','.','#'},
                {'#','.','.','#','.','#','.','#','.','#','.','#'},
                {'#','#','.','#','.','#','.','#','.','#','.','#'},
                {'#','.','.','.','.','.','.','.','.','#','.','#'},
                {'#','#','#','#','#','#','.','#','#','#','.','#'},
                {'#','.','.','.','.','.','.','#','.','.','.','#'},
                {'#','#','#','#','#','#','#','#','#','#','#','#'}};

        char[][] mazeTwo = {
                {'#','#','#','#','#','#','#','#','#','#','#','#'},
                {'#','.','#','.','.','#','.','.','.','.','.','#'},
                {'#','.','#','#','.','.','.','#','.','#','#','#'},
                {'#','.','#','#','#','.','#','#','.','.','.','#'},
                {'#','.','.','.','#','.','.','#','.','#','#','#'},
                {'#','.','#','.','#','#','.','#','.','#','#','#'},
                {'#','.','#','.','.','#','.','#','.','.','.','#'},
                {'#','.','#','#','.','#','.','#','#','.','#','#'},
                {'#','.','.','#','.','#','.','#','.','.','#','#'},
                {'#','#','.','#','.','#','.','#','.','#','#','#'},
                {'.','.','.','#','.','.','.','#','.','.','.','.'},
                {'#','#','#','#','#','#','#','#','#','#','#','#'}};

        char[][] mazeThree = {
                {'#','#','#','#','#','#','#','#','#','#','#','#'},
                {'#','.','.','.','.','#','.','.','.','#','.','.'},
                {'#','.','#','#','.','#','#','#','.','#','.','#'},
                {'#','.','#','.','.','#','.','.','.','#','.','#'},
                {'#','.','#','.','#','#','.','#','.','#','.','#'},
                {'#','.','#','.','#','.','.','#','.','.','.','#'},
                {'#','.','#','.','#','#','.','#','#','.','#','#'},
                {'.','.','#','.','.','#','.','#','.','.','#','#'},
                {'#','#','#','#','.','#','.','#','.','#','.','#'},
                {'#','.','.','#','.','#','.','#','.','#','.','#'},
                {'#','#','.','.','.','.','.','#','.','.','.','#'},
                {'#','#','#','#','#','#','#','#','#','#','#','#'}};

        // Choose an integer to select an option for a maze

        Scanner input = new Scanner(System.in);

        System.out.println("******************************************\n" +
                           "*               MAZE SOLVER              *\n" +
                           "******************************************\n");
        System.out.print("Would you like to solve maze 1, 2, or 3:");
        while (!input.hasNextInt()){
            input.nextLine();
            System.out.print("Please enter 1 2 or 3 (Nice try):");
        }
        int mazeSelection = input.nextInt();
        input.nextLine();

        // TODO: Need speeds and are those weird exceptions still noted in method
        //       definition? Need try catch blocks instead.

        switch (mazeSelection){
            case (1): new MazeTraversal(mazeOne, 500); break;
            case (2): new MazeTraversal(mazeTwo, 500); break;
            case (3): new MazeTraversal(mazeThree, 500); break;

            default:
                System.out.println("That wasn't an option, so we'll go with 1");
                new MazeTraversal(mazeOne, 500);
        }
    }
}
