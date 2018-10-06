import java.security.SecureRandom;

public class HangmanGame {
    String goal;
    SecureRandom randomGen = new SecureRandom();

    public HangmanGame(String[] words) {
        goal = words[randomGen.nextInt(words.length)];
        System.out.println("Goal:"+goal);
    }
}
