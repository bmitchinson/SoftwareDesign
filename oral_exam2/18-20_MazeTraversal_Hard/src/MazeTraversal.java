/**
 * MazeTraversal is a class that represents the recursive solution to navigating
 * from "Start" to "Finish" through a 12x12 character array. When constructed and
 * given a maze, it finds it's entry point within {@link #MazeTraversal(char[][], int)}
 * and then begins it's recursive calls to {@link #navMaze(char[][], int, int)}.
 * It also utilizes {@link #printMaze(char[][])} and {@link #clearScreen()} to
 * clear the terminal and print the current maze, with an intentional delay
 * in-between to simulate animation. The class also holds it's initial and final
 * maze for reference if needed.
 *
 * @author Ben Mitchinson
 * @see #MazeTraversal(char[][], int)
 * @see #navMaze(char[][], int, int)
 * @see #printMaze(char[][])
 * @see #clearScreen()
 */
public class MazeTraversal {
    private char[][] originalMaze;
    private char[][] finalMaze;

    /**
     * The constructor for {@link MazeTraversal} finds the entry point to the given
     * 12x12 character array "mazeToNav". It then waits 6 seconds for the sake of
     * presentation, and begins the first recursive call to {@link #navMaze(char[][], int, int)}.
     * It utilizes {@link #printMaze(char[][])} to show the initial maze given
     * before progress begins.
     *
     * @param mazeToNav 12x12 Maze to navigate
     * @param speed     Time to delay between printing screens.
     * @see MazeTraversal
     * @see #printMaze(char[][])
     * @see #navMaze(char[][], int, int)
     */
    public MazeTraversal(char[][] mazeToNav, int speed) {
        // Acts as a driver for the recursion
        int xLoc = 0;
        int yLoc = 0;

        originalMaze = mazeToNav;

        // iterate through loop to find entrance point
        for (int y = 0; y < 12; y++) {
            if (mazeToNav[y][0] == '.') {
                yLoc = y;
                y = 13; // exit loop
            }
        }

        // start the recursion function to navigate the maze
        printMaze(mazeToNav);
        System.out.println("Entry detected at X:" + xLoc + " Y:" + yLoc);
        System.out.print("Starting maze solve in ");
        for (int i = 6; i > 0; i--) {
            System.out.print(i + "...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        navMaze(mazeToNav, xLoc, yLoc);
    }

    /**
     * The navMaze method is used recursively to "move" through a 12x12 character
     * maze. It checks the characters in each "direction" from the perspective of
     * xLoc and yLoc. If a possible move is detected (meaning a '.' is found),
     * navMaze is called recursively, moving the character in that direction.
     * <p>
     * It's base case is the scenario in which no moves are possible, or it has
     * ended up at the right most column, thus finding the end. From there, the
     * next search of direction is called
     * <p>
     * It uses {@link #printMaze(char[][])} and {@link #clearScreen()} to print
     * it's continuous progress through the maze.
     *
     * @param maze The maze to navigate, possibly marked by an X with previous
     *             progress
     * @param xLoc The current location of navigation in the x direction
     * @param yLoc The current location of navigation in the y direction
     * @return true if a move is possible, false if not.
     * @see #printMaze(char[][])
     * @see #clearScreen()
     */
    private boolean navMaze(char[][] maze, int xLoc, int yLoc) {
        clearScreen();
        printMaze(maze);
        if (xLoc == 11 && maze[yLoc][xLoc] == '.') {
            maze[yLoc][xLoc] = '!';
            clearScreen();
            printMaze(maze);
            System.out.println("Maze Solved");
            return true;
        } else {
            boolean wayOut;
            char[][] backup = maze;
            maze[yLoc][xLoc] = 'x';
            if (xLoc + 1 < 12 && maze[yLoc][xLoc + 1] == '.') { // right
                System.out.println("Trying right");
                wayOut = (navMaze(maze, xLoc + 1, yLoc));
                if (!wayOut) {
                    maze = backup;
                } else {
                    return true;
                }
            }
            if (yLoc + 1 < 12 && maze[yLoc + 1][xLoc] == '.') { // down
                System.out.println("Trying down");
                wayOut = (navMaze(maze, xLoc, yLoc + 1));
                if (!wayOut) {
                    maze = backup;
                } else {
                    return true;
                }
            }
            if (yLoc - 1 >= 0 && maze[yLoc - 1][xLoc] == '.') { // up
                System.out.println("Trying up");
                wayOut = (navMaze(maze, xLoc, yLoc - 1));
                if (!wayOut) {
                    maze = backup;
                } else {
                    return true;
                }
            }
            if (xLoc - 1 >= 0 && maze[yLoc][xLoc - 1] == '.') { // left
                System.out.println("Trying left");
                wayOut = (navMaze(maze, xLoc - 1, yLoc));
                if (!wayOut) {
                    maze = backup;
                } else {
                    return true;
                }
            }
            return false;
        }
    }

    /**
     * Prints the given maze (12x12 character array) with proper spacing.
     *
     * @param maze A 12x12 character array
     * @see #printMaze(char[][])
     */
    private void printMaze(char[][] maze) {
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 12; j++) {
                System.out.print(maze[i][j] + " ");
            }
            System.out.print("\n");
        }
        System.out.println();
    }

    /**
     * Prints 50 blank lines to clear the screen of the terminal. Some call it
     * annoying, I call it genius.
     *
     * @see MazeTraversal
     */
    private void clearScreen() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 100; i++) {
            System.out.println();
        }
    }

}