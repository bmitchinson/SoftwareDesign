import javax.swing.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NewGame {
    public static void main(String args[]) {
        ExecutorService worker = Executors.newFixedThreadPool(3);
        worker.execute(new StartDealer());
        worker.execute(new StartClient());
        worker.execute(new StartClient());
    }
}
