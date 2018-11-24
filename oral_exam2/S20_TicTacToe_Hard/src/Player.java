abstract class Player {

    public Board board;
    public char symbol;

    public Player(char symbol){
        this.symbol = symbol;
    }

    public void giveBoard(Board board){
        this.board = board;
    }

    public abstract void makeMove();


}
