public class MazeTraversal {
    private char[][] originalMaze;
    private char[][] finalMaze;

    public MazeTraversal(char[][] mazeToNav){
        // Acts as a driver for the recursion
        int xLoc = 0;
        int yLoc = 0;

        // iterate through loop to find entrance point
        for (int y=0; y < 12; y++){
            if (mazeToNav[y][0] == '.'){
                yLoc = y;
                y = 13; // exit loop
            }
        }

        // start the recursion function to navigate the maze
        navMaze(mazeToNav, xLoc, yLoc);
    }

    private void navMaze(char[][] maze, int xLoc, int yLoc){
        // TODO: progress through maze by saving maze, placing progress x,
        //       and trying all directions that are open, (aren't x's or walls)
        // TODO: Base case for exit to stop calling open spots, print final
        System.out.println("Starting maze nav at en X:" +
        Integer.toString(xLoc) + " Y:" +
        Integer.toString(yLoc));
        printMaze(maze);
        // TODO: return final if set? Does that matter? Or just stop printing
        //       and leave "solution solved" in mazeTraversal
    }

    private void printMaze(char[][] maze){
        for (int i = 0; i<12; i++){
            for (int j = 0; j<12; j++){
                System.out.print(maze[i][j] + " ");
            }
            System.out.print("\n");
        }
    }

}