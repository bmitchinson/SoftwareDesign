import javax.swing.*;
import java.awt.*;
import java.io.IOException;
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
                    System.out.println(LocalTime.now().format(timeFormat) + message);
                }
        );
    }

    public boolean isGameOver() {
        if (playersHands[0].getBlackjackTotal() > 21){
            players[0].sendMessage("GameOver", "Lose");
            players[1].sendMessage("GameOver", "Win");
            return true;
        }
        else if (playersHands[1].getBlackjackTotal() > 21){
            players[0].sendMessage("GameOver", "Win");
            players[1].sendMessage("GameOver", "Lose");
            return true;
        }
        else { return false; }
    }

    private class Player implements Runnable {
        private Socket connection;
        private Scanner input;
        private Formatter output;
        private String playerName;
        private String otherPlayerName;
        private int playerIndex;
        private int otherPlayerIndex;
        private boolean suspended = true;
        private boolean hit;

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
                sendMessage("Title", playerName);

                if (playerName.equals("Player One")) {
                    otherPlayerIndex = 1;
                    playerIndex = 0;
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
                    otherPlayerIndex = 0;
                    playerIndex = 1;
                    sendMessage("Cards",playersHands[1].pileAsStrings());
                    sendMessage("OpCards",playersHands[0].pileAsStrings());
                }

                displayMessage(playerName + " is entering the gameplay loop");
                while (!isGameOver()) {
                    if(currentPlayer.equals(playerName)){
                        displayMessage("Sending signal to enable buttons on " +
                        playerName);
                        sendMessage("Buttons","On");
                        // Send other players previous choices:
                        sendMessage("OpCards",
                                playersHands[otherPlayerIndex].pileAsStrings());
                        displayMessage(playerName + " is waiting on button hit");
                        hit = getHit();
                        displayMessage(playerName + " got button hit");
                        // calculate that input
                        if(hit){
                            playersHands[playerIndex].addToPile(deck.removeFromPile(1));
                            sendMessage("PlayerCards",
                                    playersHands[playerIndex].pileAsStrings());
                        }
                        sendMessage("Buttons","Off");
                        sendMessage("Message","Turn finished, so waiting for other player");
                        currentPlayer = otherPlayerName;
                        gameLock.lock();
                        otherPlayerTurn.signal();
                        otherPlayerTurn.await();
                        gameLock.unlock();
                    }
                    else{
                        sendMessage("Message",
                                "Waiting for " + otherPlayerName);
                        gameLock.lock();
                        otherPlayerTurn.await();
                        gameLock.unlock();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                displayMessage(playerName + " left gameplay loop");
                try {
                    connection.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                    System.exit(1);
                }
            }
        }

        public void sendMessage(String type, String message) {
            displayMessage("Sent: " + type + " to client " + playerName);
            output.format(type + "\n");
            output.flush();
            output.format(message + "\n");
            output.flush();
        }

        public void sendMessage(String type, String[] messages) {
            output.format(type + "\n");
            output.flush();
            for (String message : messages) {
                displayMessage("Sent: " + message + " to " + playerName);
                output.format(message + "\n");
                output.flush();
            }
            output.format("END\n");
            output.flush();
        }

        private boolean getHit(){
            if (input.hasNextLine()){
                return input.nextLine().equals("Hit");
            }
            System.out.println(playerName + ": SHOULD NEVER HAPPEN");
            return false;
        }

        // TODO:
        // public void opponentHandUpdate(Hand newHand)

        public void setSuspended(boolean status) {
            suspended = status;
        }
    }
}
