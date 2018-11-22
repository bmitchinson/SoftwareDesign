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

// TODO: JDoc that are transparent about the reference of "fig28_11_14"
public class Dealer extends JFrame {

    // Game Logic
    private Pile deck;
    private Player[] players;
    private String currentPlayer;
    private Pile[] playersHands = {new Pile(false),
            new Pile(false)};

    // GUI Elements
    private JTextArea outputArea;

    // Networking + Threading
    private ServerSocket server;
    private DateTimeFormatter timeFormat;
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
        timeFormat = DateTimeFormatter.ofPattern("hh:mm:ss.SSS - ");

        players = new Player[2];
        currentPlayer = "Player One";

        deck = new Pile(true);
        playersHands[0] = deck.removeFromPile(2);
        playersHands[1] = deck.removeFromPile(2);

        try {
            server = new ServerSocket(23516, 12); // Setup socket
        } catch (IOException ioException) {
            System.out.println("\nPort 23516 is already in use, " +
                    "do you already have a server running?\n");
            ioException.printStackTrace();
            System.exit(1);
        }

        outputArea = new JTextArea();
        outputArea.setText(LocalTime.now().format(timeFormat) +
                "Server opened and awaiting connections\n");
        outputArea.setEditable(false);
        add(new JScrollPane(outputArea), BorderLayout.CENTER);
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
            runGame.execute(players[0]);
            displayMessage("Waiting for second connection");
            players[1] = new Player(server.accept(), "Player Two");
            runGame.execute(players[1]);

        } catch (IOException ioException) {
            ioException.printStackTrace();
            System.exit(1);
        }
        gameLock.lock();
        try {
            players[0].setSuspended(false);
            displayMessage("Waking Player One with otherPlayerConnected method");
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

    public boolean isGameOver() {
        // TODO: Get totals from each player's pile, need to write player
        //       method to getTotal -> hand.getBlackJackTotal
        return false;
    }

    // TODO: Method to react to client action

    // TODO: Method for "isGameOver" loop condition for players biased on game status

    // TODO: Are connections closed anywhere? Upon game over or exit? Do I need a method for that?

    private class Player implements Runnable {
        private Socket connection;
        private Scanner input;
        private Formatter output;
        private String playerName;
        private String otherPlayerName;
        private boolean suspended = true;

        // TODO: add an initial Hand parameter
        public Player(Socket socket, String name) {
            this.playerName = name;
            if (playerName.equals("Player One")){
                otherPlayerName = "Player Two";
            } else { otherPlayerName = "Player One"; }

            this.connection = socket;

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

                sendMessage("Title", playerName);;

                if (playerName.equals("Player One")) {
                    sendMessage("Cards", playersHands[0].pileAsStrings());
                    displayMessage(playerName + " waiting for Player Two");
                    gameLock.lock();
                    try {
                        while (suspended) {
                            otherPlayerConnected.await();
                        }
                    } catch (InterruptedException exception) {
                        exception.printStackTrace();
                    } finally {
                        gameLock.unlock();
                    }
                }
                else{
                    sendMessage("Cards",playersHands[1].pileAsStrings());
                }
                displayMessage(playerName + " is entering the gameplay loop");
                while (!isGameOver()) {
                    if(currentPlayer.equals(playerName)){
                        sendMessage("Message","Starting Turn");
                        // Send other players previous choices:
                        // TODO: Need to hold game data in dealer, not just players
                        // Need to enable buttons
                        // wait for input
                        // calculate that input
                        // interpret calcuations:
                        //     game over check?
                        //     continue await next turn. (case of 21 is this)
                        //     send outcome
                        // switch active player
                        // wake up other player
                        // await for wake from other player

                        sendMessage("Message","Turn finished, so waiting for other player");
                        currentPlayer = otherPlayerName;
                        otherPlayerTurn.signal();
                        otherPlayerTurn.await();
                    }
                    else{
                        sendMessage("Message",
                                "Waiting for " + otherPlayerName);
                        otherPlayerTurn.await();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                displayMessage("Left gameplay loop");
                try {
                    connection.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                    System.exit(1);
                }
            }
        }

        public void sendMessage(String type, String message) {
            displayMessage("Sent: " + type + " to client");
            output.format(type + "\n");
            output.flush();
            output.format(message + "\n");
            output.flush();
        }

        public void sendMessage(String type, String[] messages) {
            output.format(type + "\n");
            displayMessage("Sent: " + type + " to client");
            output.flush();
            for (String message : messages) {
                output.format(message + "\n");
                output.flush();
            }
            output.format("END\n");
            output.flush();
        }

        // TODO:
        // public void opponentHandUpdate(Hand newHand)

        public void setSuspended(boolean status) {
            suspended = status;
        }
    }
}
