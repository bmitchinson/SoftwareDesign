import javax.swing.*;
import java.awt.*;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Formatter;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client extends JFrame implements Runnable {
    private JTextArea displayArea;
    private Socket connection;
    private Scanner input;
    private Formatter output;
    private boolean myTurn;
    private String myName;

    public Client() {
        displayArea = new JTextArea(4,30);
        displayArea.setEditable(false);
        add(new JScrollPane(displayArea), BorderLayout.SOUTH);

        displayArea = new JTextArea();
        add(displayArea, BorderLayout.CENTER);

        setSize(400, 400);
        setResizable(false);
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);
        setVisible(true);
        startClient();
    }

    public void startClient() {
        try
        {
            connection = new Socket(InetAddress.getByName("127.0.0.1"),23516);
            input = new Scanner(connection.getInputStream());
            output = new Formatter(connection.getOutputStream());
        } catch (IOException ioException) {
            System.out.println("\nConnection failed? Did you try starting a server first?\n");
            ioException.printStackTrace();
            System.exit(1);
        }

        ExecutorService worker = Executors.newFixedThreadPool(1);
        worker.execute(this);
    }

    public void run() {
        // TODO: Coordinate server giving client it's name
        //myName = input.nextLine();
        //myTurn = myName.equals(myName.equals("Player One"));

        while (true) {
            if (input.hasNextLine())
                processMessage(input.nextLine());
        }
    }

    private void processMessage(String message){
        // TODO: Process server commands
    }

    // TODO: sendHit(), sendStay()

    private void displayMessage(final String messageToDisplay) {
        SwingUtilities.invokeLater(
                new Runnable() {
                    public void run() {
                        displayArea.append(messageToDisplay); // updates output
                    }
                }
        );
    }

}
