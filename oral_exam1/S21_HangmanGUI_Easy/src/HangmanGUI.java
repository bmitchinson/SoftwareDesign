public class HangmanGUI {

    public static void main(String[] args) {
        String[] words = {"cat", "dog", "man", "sky", "dim", "big"};
        HangmanGame game = new HangmanGame(words);
        game.setVisible(true);
    }

}
