import javax.swing.*;
import java.awt.*;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Formatter;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// TODO: JDoc that's transparent about the reference of "fig28_11_14"
public class Dealer extends JFrame {
    private final static int P_ONE = 1;
    private final static int P_TWO = 2;
    // TODO: Create a "Hand" class to represent cards, but also their total.
    //       Dynamically factors in the ace based on what's held. Constructed with
    //       an initial deal. Use this instead of the Card array below.
    private Card[] cards;
    private JTextArea outputArea;
    private Player[] players;
    private ServerSocket server;
    private int currentPlayer;
    private ExecutorService runGame;
    private Lock gameLock;
    private Condition otherPlayerConnected;
    private Condition otherPlayerTurn;

    public Dealer() {
        super("Dealer (Network Server)");

        runGame = Executors.newFixedThreadPool(2);
        gameLock = new ReentrantLock();
        otherPlayerConnected = gameLock.newCondition();
        otherPlayerTurn = gameLock.newCondition();

        // TODO: Logic to instantiate shuffled deck

        players = new Player[2];
        currentPlayer = P_ONE;

        try {
            server = new ServerSocket(23516, 2); // Setup socket
        } catch (IOException ioException) {
            ioException.printStackTrace();
            System.exit(1);
        }

        outputArea = new JTextArea();
        add(outputArea, BorderLayout.CENTER);
        outputArea.setText("Server awaiting connections...\n");
        setSize(400, 400);
        setVisible(true);

    }

    public void execute() {
        try {
            players[0] = new Player(server.accept(), );
        }
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

    private void print(String message) {
        outputArea.append("\n" + message);
    }

    private class Player implements Runnable {
        private Socket connection;
        private Scanner input;
        private Formatter output;
        private String playerName;
        // TODO: Do I need this? Feel like Locks and all that should take care of it
        private boolean suspended = true; // whether thread is suspended

        // TODO: Make this the hand object. replace all other card objects with hand
        private Card[] heldCards;

        // TODO: Change this initial Deal to an initial Hand
        public Player(Socket socket, String name, Card initDeal[]) {
            playerName = name;
            heldCards = initDeal;
            connection = socket;

            try {
                input = new Scanner(connection.getInputStream());
                output = new Formatter(connection.getOutputStream());
            } catch (IOException ioException) {
                ioException.printStackTrace();
                System.exit(1);
            }
        }

        // TODO:
        // public void opponentHandupdate(Hand newHand)

        public void run() {
            displayMessage(String.format("%s connected", playerName));
            // TODO NEXT: side by side tic tac and fill in dealer
        }

        public boolean isOver() {
            // if sum of currentCards exceeds 21, including ace dual value
            return false;
        }
    }

    private void displayMessage(final String message) {
        SwingUtilities.invokeLater(
                () -> {
                    outputArea.append(message+"\n"); // add message
                }
        );
    }

}
