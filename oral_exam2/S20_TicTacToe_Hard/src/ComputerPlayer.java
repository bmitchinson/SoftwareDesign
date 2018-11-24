import javax.swing.*;

public class ComputerPlayer extends Player {

    public ComputerPlayer(char symbol){
        super(symbol);
    }

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
