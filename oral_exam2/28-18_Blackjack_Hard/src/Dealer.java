import javax.swing.*;
import java.awt.*;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Formatter;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// TODO: JDoc that's transparent about the reference of "fig28_11_14"
public class Dealer extends JFrame {
    private Card[] cards;
    private JScrollPane outputAreaPane;
    private JTextArea outputArea;
    private Player[] players;
    private ServerSocket server;
    private DateTimeFormatter timeFormat;
    private int currentPlayer;
    private ExecutorService runGame;
    private Lock gameLock;
    private Condition otherPlayerConnected;
    private Condition otherPlayerTurn;

    public Dealer() {
        super("Dealer (Server Log)");

        runGame = Executors.newFixedThreadPool(2);
        gameLock = new ReentrantLock();
        otherPlayerConnected = gameLock.newCondition();
        otherPlayerTurn = gameLock.newCondition();

        timeFormat = DateTimeFormatter.ofPattern("hh:mm:ss.SSS: ");

        // TODO: Logic to instantiate shuffled deck

        players = new Player[2];
        currentPlayer = 0;

        try {
            server = new ServerSocket(23516, 2); // Setup socket
        } catch (IOException ioException) {
            System.out.println("\nPort 23516 is already in use, " +
                    "do you already have a server running?\n");
            ioException.printStackTrace();
            System.exit(1);
        }

        outputArea = new JTextArea();
        outputAreaPane = new JScrollPane(outputArea);
        add(outputAreaPane, BorderLayout.CENTER);
        outputArea.setText(LocalTime.now().format(timeFormat) +
                "Server opened and awaiting connections\n");
        setSize(400, 300);
        setResizable(false);
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);
        setVisible(true);

    }

    public void execute() {
        try {
            displayMessage("Waiting for first connection");
            players[0] = new Player(server.accept(), "Player One");
            displayMessage("First connection received");
            runGame.execute(players[0]);
            displayMessage("Waiting for second connection");
            players[1] = new Player(server.accept(), "Player Two");
            displayMessage("Second connection received");
            runGame.execute(players[1]);
        } catch (IOException ioException) {
            ioException.printStackTrace();
            System.exit(1);
        }

        gameLock.lock();
        try {
            players[0].setSuspended(false);
            displayMessage("Waking with otherPlayerConnected method");
            otherPlayerConnected.signal();
        } finally {
            gameLock.unlock();
        }
    }

    private void displayMessage(final String message) {
        SwingUtilities.invokeLater(
                () -> {
                    outputArea.append(LocalTime.now().format(timeFormat) + message + "\n"); // add message
                }
        );
    }

    // TODO: Method to react to client action

    // TODO: Method for "isGameOver" loop condition for players biased on game status

    // TODO: Are connections closed anywhere? Upon game over or exit? Do I need a method for that?

    private class Player implements Runnable {
        private Socket connection;
        private Scanner input;
        private Formatter output;
        private String playerName;
        private boolean suspended = true;

        // TODO: Make this the hand object. replace all other card objects with hand
        private Card[] heldCards;

        // TODO: add an initial Hand parameter
        public Player(Socket socket, String name) {
            playerName = name;
            //heldCards = initDeal;
            connection = socket;
            displayMessage("Internal " + playerName + " spun up.");

            try {
                input = new Scanner(connection.getInputStream());
                output = new Formatter(connection.getOutputStream());
            } catch (IOException ioException) {
                ioException.printStackTrace();
                System.exit(1);
            }
        }

        public void run() {
            try {
                displayMessage(playerName + " running");
                if (playerName.equals("Player One")) {
                    displayMessage(playerName + " waiting for Player Two");
                    gameLock.lock();
                    try {
                        while (suspended) {
                            otherPlayerConnected.await();
                        }
                    } catch (InterruptedException exception) {
                        exception.printStackTrace();
                    } finally {
                        displayMessage(playerName + " woke because Player Two has connected");
                        gameLock.unlock();
                    }

                } else {
                    output.format("Player Two connected, please wait\n");
                    output.flush();
                }
                displayMessage(playerName + " is entering the endless gameplay loop");
                while (!isGameOver()) {
                    // TODO: Gameplay Loop
                }
            } finally {
                displayMessage(playerName + " has exited gameplay loop");
                try {
                    connection.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                    System.exit(1);
                }
            }
        }

        // TODO:
        // public void opponentHandUpdate(Hand newHand)

        public void setSuspended(boolean status) {
            suspended = status;
        }

        public boolean isGameOver() {
            // if sum of currentCards exceeds 21, including ace dual value
            return false;
        }
    }
}
