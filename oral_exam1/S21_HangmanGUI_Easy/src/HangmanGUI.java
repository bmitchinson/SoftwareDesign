public class HangmanGUI {

    /**
     * The driver class for a game of hangman
     *
     * @param args unused
     */
    public static void main(String[] args) {
        String[] words = {"cat", "dog", "man", "sky", "dim", "big"};
        HangmanGame game = new HangmanGame(words);
        game.setVisible(true);
    }

}
