public class MazeTraversal {
    private char[][] originalMaze;
    private char[][] finalMaze;
    private int speed;
    // TODO: Implement speed selection

    // TODO: Remove InterruptedExceptions with try catch blocks
    // TODO: JDocs
    public MazeTraversal(char[][] mazeToNav, int speed) throws InterruptedException {
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
        System.out.println("Entry detected at X:"+xLoc+" Y:"+yLoc);
        System.out.print("Starting maze solve in ");
        for (int i = 6; i > 0; i--){
            System.out.print(i + "...");
            Thread.sleep(1000);
        }
        navMaze(mazeToNav, xLoc, yLoc);
    }

    private boolean navMaze(char[][] maze, int xLoc, int yLoc) throws InterruptedException {
        clearScreen();
        printMaze(maze);
        if (xLoc == 11 && maze[yLoc][xLoc] == '.') {
            maze[yLoc][xLoc] = '!';
            clearScreen();
            printMaze(maze);
            System.out.println("Maze Solved");
            return true;
        }
        else {
            boolean wayOut;
            char[][] backup = maze;
            maze[yLoc][xLoc] = 'x';
            if (xLoc + 1 < 12 && maze[yLoc][xLoc + 1] == '.') { // right
                System.out.println("Trying right");
                wayOut = (navMaze(maze, xLoc + 1, yLoc));
                if (!wayOut) {
                    maze = backup;
                } else{ return true; }
            }
            if (yLoc + 1 < 12 && maze[yLoc + 1][xLoc] == '.') { // down
                System.out.println("Trying down");
                wayOut = (navMaze(maze, xLoc, yLoc + 1));
                if (!wayOut) {
                    maze = backup;
                }
                else{ return true; }
            }
            if (yLoc - 1 >= 0 && maze[yLoc - 1][xLoc] == '.') { // up
                System.out.println("Trying up");
                wayOut = (navMaze(maze, xLoc, yLoc - 1));
                if (!wayOut) {
                    maze = backup;
                }
                else{ return true; }
            }
            if (xLoc - 1 >= 0 && maze[yLoc][xLoc - 1] == '.') { // left
                System.out.println("Trying left");
                wayOut = (navMaze(maze, xLoc - 1, yLoc));
                if (!wayOut) {
                    maze = backup;
                } else{ return true; }
            }
            return false;
        }
    }

    private void printMaze(char[][] maze) {
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 12; j++) {
                System.out.print(maze[i][j] + " ");
            }
            System.out.print("\n");
        }
        System.out.println();
    }

    private void clearScreen() throws InterruptedException {
        Thread.sleep(500);
        for (int i = 0; i < 100; i++){
            System.out.println();
        }
    }

}