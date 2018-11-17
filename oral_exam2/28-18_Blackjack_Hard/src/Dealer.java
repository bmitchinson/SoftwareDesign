import javax.swing.*;
import java.awt.*;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Dealer extends JFrame {
    private JTextArea displayArea;
    private ObjectOutputStream outputOne;
    private ObjectOutputStream outputTwo;
    private ObjectInputStream inputOne;
    private ObjectInputStream inputTwo;
    private Player[] players;
    private Card[] cards;
    private ServerSocket server;
    private Socket connectionOne;
    private Socket connectionTwo;
    private int playersConnected = 0;

    public Dealer() {
        displayArea = new JTextArea();
        add(displayArea, BorderLayout.CENTER);
        setSize(400, 400);
        setVisible(true);

    }

    public void runServer() {
        try {
            server = new ServerSocket(23516, 100); // create ServerSocket

            while (true) {
                try {
                    waitForTwoConnections(); // wait for a connection
                    getStreams(); // get input & output streams
                    processConnection(); // process connection
                } catch (EOFException eofException) {
                    System.out.println("Server terminated connection");
                } finally {
                    closeConnection(); //  close connection
                }
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    private void waitForTwoConnections() throws IOException {
        print("Waiting for player 1 connection");
        connectionOne = server.accept(); // allow server to accept connection
        playersConnected++;
        System.out.println("Connection " + playersConnected + " received from: " +
                connectionOne.getInetAddress().getHostName());
        System.out.println("Waiting for player 2 connection");
        connectionTwo = server.accept(); // allow server to accept connection
        playersConnected++;
        System.out.println("Connection " + playersConnected + " received from: " +
                connectionTwo.getInetAddress().getHostName());
    }

    // get streams to send and receive data
    private void getStreams() throws IOException {
        // set up output stream for objects
        output = new ObjectOutputStream(connection.getOutputStream());
        output.flush(); // flush output buffer to send header information

        // set up input stream for objects
        input = new ObjectInputStream(connection.getInputStream());

        System.out.println("Got I/O streams");
    }

    private void processConnection() throws IOException {
        try // send object to client
        {
            output.writeObject("SERVER>>> " + "First Hello");
            output.flush(); // flush output to client
            System.out.println("\nSERVER>>> " + "First Hello");
        } catch (IOException ioException) {
            System.out.println("Error writing object");
        }
    }

    // close streams and socket
    private void closeConnection() {
        System.out.println("\nTerminating connection\n");

        try {
            output.close(); // close output stream
            input.close(); // close input stream
            connection.close(); // close socket
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    private void print(String message){
        displayArea.append("\n"+message);
    }

    private class Player {
        private int playerNum;
        private Card[] currentCards;

        public Player(int num, Card initDeal[]) {
            int playerNum = num;
            currentCards = initDeal;
        }

        public boolean isOver() {
            // if sum of currentCards exceeds 21, including ace dual value
            return false;
        }
    }

    private void firstDeal() {

    }

    private void deal(Player player) {

    }

    private void hit(Player player) {

    }
}
