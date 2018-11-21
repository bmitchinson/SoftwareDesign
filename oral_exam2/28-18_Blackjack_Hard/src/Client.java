import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Formatter;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import static javax.swing.ScrollPaneConstants.*;

public class Client extends JFrame implements Runnable {

    // GUI Elements
    private JTextArea outputArea;
    private JScrollPane outputAreaScroll;
    private Background background;
    private DateTimeFormatter timeFormat;
    private JPanel buttonPanel;
    private JButton hitButton;
    private JButton stayButton;

    // Network Connectivity
    private Socket connection;
    private Scanner input;
    private Formatter output;

    // Client Game Logic
    private boolean myTurn;
    private String myName;

    public Client() {

        timeFormat = DateTimeFormatter.ofPattern("hh:mm:ss.SSS - ");

        outputArea = new JTextArea();
        outputArea.setText(LocalTime.now().format(timeFormat) + "Client opened" + "\n");
        outputArea.setEditable(false);
        outputArea.setLineWrap(true);
        DefaultCaret caret = (DefaultCaret)outputArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        outputAreaScroll = new JScrollPane();
        outputAreaScroll.setViewportView(outputArea);
        outputAreaScroll.setPreferredSize(new Dimension(400,100));

        background = new Background("/img/table.png");

        hitButton = new JButton("Hit");
        hitButton.setPreferredSize(new Dimension(175,50));
        stayButton = new JButton("Stay");
        stayButton.setPreferredSize(new Dimension(175,50));

        buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(400,80));
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEADING,15,15));
        buttonPanel.add(hitButton);
        buttonPanel.add(stayButton);

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        add(outputAreaScroll);
        add(background);
        add(buttonPanel);

        setSize(400, 500);
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
            // TODO: Get name from server? auto from run?
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
        // myName = input.nextLine();
        // myTurn = myName.equals(myName.equals("Player One"));
        displayMessage("Beginning Run");
        setButtonsActive(false);
        while (true) {
            displayMessage("Looking for network input.");
            if(input.hasNextLine()){
                displayMessage("Input does have next, processing");
                processMessage(input.nextLine());
            }
        }
    }

    private void processMessage(String message){
        displayMessage("Received message from server: "+message);
        if (message.equals("Title")){
            String nextMessage = input.nextLine();
            displayMessage("Setting title to: " + nextMessage);
            setTitle(nextMessage);
        }
        else{
            displayMessage("Unsure how to process that.");
        }
    }

    // TODO: sendHit(), sendStay()

    private void displayMessage(final String message) {
        SwingUtilities.invokeLater(
                () -> {
                    outputArea.append(LocalTime.now().format(timeFormat) + message + "\n"); // updates output
                    JScrollBar scrollBar = outputAreaScroll.getVerticalScrollBar();
                    scrollBar.setValue( scrollBar.getMaximum() );
                }
        );
    }

    private void setButtonsActive(boolean status){
        hitButton.setEnabled(status);
        stayButton.setEnabled(status);
    }

    private class Background extends JPanel {
        BufferedImage bgImage;

        Background(String url){
            try {
                System.out.println(this.getClass().getResource(url));
                bgImage = ImageIO.read(this.getClass().getResource(url));
            } catch (IOException e) {
                e.printStackTrace();
            }
            setBorder(BorderFactory.createLineBorder(Color.black));
            setPreferredSize(new Dimension(500,328));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(bgImage, 0, 0, null);
        }

    }

}
