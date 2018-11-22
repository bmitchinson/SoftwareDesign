import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Formatter;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client extends JFrame implements Runnable {

    // GUI Elements
    private JTextArea outputArea;
    private JScrollPane outputAreaScroll;
    private Background background;
    private DateTimeFormatter timeFormat;
    private JPanel buttonPanel;
    private JButton hitButton;
    private JButton stayButton;
    private JPanel opponentSpace;
    private JLabel opponentCards;
    private JLabel opponentTotal;
    private JPanel playerSpace;
    private JLabel playerCards;
    private JLabel playerTotal;
    private JLabel status;
    private Client self;

    // Network Connectivity
    private Socket connection;
    private Scanner input;
    private Formatter output;

    // Gameplay
    private boolean gameover = false;
    private Pile playerPile = new Pile(false);
    private Pile opponentPile = new Pile(false);

    public Client() {
        // Needed to revalidate and repaint entire after component updates
        self = this;

        timeFormat = DateTimeFormatter.ofPattern("hh:mm:ss.SSS - ");

        outputArea = new JTextArea();
        outputArea.setText(LocalTime.now().format(timeFormat) + "Client opened" + "\n");
        outputArea.setEditable(false);
        outputArea.setLineWrap(true);
        DefaultCaret caret = (DefaultCaret) outputArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        outputAreaScroll = new JScrollPane();
        outputAreaScroll.setViewportView(outputArea);
        outputAreaScroll.setPreferredSize(new Dimension(400, 100));

        background = new Background("/img/table.png");

        hitButton = new JButton("Hit");
        hitButton.setPreferredSize(new Dimension(175, 50));
        hitButton.addActionListener(e -> sendHit());
        stayButton = new JButton("Stay");
        stayButton.setPreferredSize(new Dimension(175, 50));
        stayButton.addActionListener(e -> sendStay());

        buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(400, 80));
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 15, 15));
        buttonPanel.add(hitButton);
        buttonPanel.add(stayButton);

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        add(outputAreaScroll);
        add(background);
        add(buttonPanel);

        setSize(400, 700);
        setResizable(false);
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);
        setVisible(true);

        startClient();
    }

    public void startClient() {
        try {
            connection = new Socket(InetAddress.getByName("127.0.0.1"), 23516);
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
        displayMessage("Beginning Run");
        setButtonsActive(false);
        while (!gameover) {
            displayMessage("Looking for network input.");
            if (input.hasNextLine()) {
                processMessage(input.nextLine());
            }
        }
        // TODO: Thread innterupt to freeze after game over? Close connections?
    }

    private void processMessage(String message) {
        displayMessage("Received message type " + message
                + " from server");
        if (message.equals("Title")) {
            setTitle(input.nextLine());
            setStatus(getTitle());
        }
        else if (message.equals("Cards")) {
            updatePile(true);
            // TODO: Remove update board
            updateBoard();
            background.refreshPlayer();
        }
        else if (message.equals("OpCards")) {
            updatePile(false);
            // TODO: Remove update board
            updateBoard();
            background.refreshOpponent();
        }
        else if (message.equals("Buttons")) {
            if(input.nextLine().equals("On")){ setButtonsActive(true); }
            else { setButtonsActive(false);}
        }
        else if (message.equals("GameOver")) {
            setButtonsActive(false);
            gameover = true;
            String type = input.nextLine();
            if (type.equals("Win")) {
                displayMessage("YOU WON :D");
                setTitle(getTitle() + " - YOU WON!");
                setStatus("YOU WON!!");
            } else if (type.equals("Lost")){
                displayMessage("...you lost :(");
                setTitle(getTitle() + "- ...you lost :(");
                setStatus("You lost :(");
            } else {
                displayMessage("Tie Game!");
                setTitle(getTitle() + "- Tie Game");
                setStatus("Tie Game");
            }
        }
        else if (message.equals("Message")) {
            displayMessage("" + input.nextLine());
        }
        else {
            displayMessage("Unsure how to process that.");
        }
    }

    private void updatePile(boolean player){
        ArrayList<Card> incomingCards = new ArrayList<>();

        String nextMessage = input.nextLine();
        while (!nextMessage.equals("END")) {
            displayMessage("Adding:" + nextMessage.charAt(0)
                    + nextMessage.charAt(1) + " to linked list");
            incomingCards.add(new Card(nextMessage.charAt(0),
                    nextMessage.charAt(1)));
            nextMessage = input.nextLine();
        }

        // Cast linked list back into card array for pile construction
        Object[] objArray = incomingCards.toArray();
        Card[] cardArray = Arrays.copyOf(objArray, objArray.length, Card[].class);
        Pile update = new Pile(cardArray);

        if (player) { playerPile = update; }
        else { opponentPile = update; }

    }

    private void sendHit(){
        displayMessage("Sending Hit to server");
        output.format("Hit\n");
        output.flush();
    }

    private void sendStay(){
        displayMessage("Sending Stay to server");
        output.format("Stay\n");
        output.flush();
    }

    private void updateBoard(){
        String playerPileString = " ";
        for (String string : playerPile.pileAsStrings()){
            playerPileString += string + " ";
        }
        String opponentPileString = " ";
        for (String string : opponentPile.pileAsStrings()){
            opponentPileString += string + " ";
        }
        displayMessage("Player Hand:"+playerPileString);
        displayMessage("Player Hand Total:"+playerPile.getBlackjackTotal());
        displayMessage("Opponent Hand:"+opponentPileString);
        displayMessage("Opponent Hand Total:"+opponentPile.getBlackjackTotal());
    }

    private void displayMessage(final String message) {
        SwingUtilities.invokeLater(
                () -> {
                    outputArea.append(LocalTime.now().format(timeFormat) + message + "\n"); // updates output
                    System.out.println(getTitle() + " " + LocalTime.now().format(timeFormat) + message);
                    JScrollBar scrollBar = outputAreaScroll.getVerticalScrollBar();
                    scrollBar.setValue(scrollBar.getMaximum());
                }
        );
    }

    private void setStatus(final String newStatus){
        SwingUtilities.invokeLater(() -> {
            status.setText(newStatus);
            self.validate();
            self.repaint();
        });
    }

    private void setButtonsActive(boolean status) {
        displayMessage("Buttons set to " + status);
        hitButton.setEnabled(status);
        stayButton.setEnabled(status);
    }

    private class CardImage extends JPanel {
        BufferedImage cardImage;

        CardImage(char suit, char value, int index){
            String url = "/img/cards/" +
                    Character.toString(suit) + Character.toString(value) +
                    ".png";
            try {
                cardImage = ImageIO.read(this.getClass().getResource(url));
                cardImage = resize(cardImage, 65,105);
            } catch (IOException e) {
                e.printStackTrace();
            }

            setBounds(index * 25, 0, 65, 100);
        }

        private BufferedImage resize(BufferedImage img, int newW, int newH){
            Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
            BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

            Graphics2D g2d = dimg.createGraphics();
            g2d.drawImage(tmp, 0, 0, null);
            g2d.dispose();

            return dimg;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(cardImage, 0, 0, null);
        }

    }

    private class Background extends JPanel {
        BufferedImage bgImage;
        JLayeredPane playerCardStack = new JLayeredPane();
        JLayeredPane opponentCardStack = new JLayeredPane();

        Background(String url) {

            try {
                bgImage = ImageIO.read(this.getClass().getResource(url));
            } catch (IOException e) {
                e.printStackTrace();
            }

            status = new JLabel();
            status.setFont(new Font("Veranda",1,30));
            status.setForeground(Color.white);
            status.setHorizontalAlignment(SwingConstants.CENTER);

            setBorder(BorderFactory.createLineBorder(Color.black));
            setPreferredSize(new Dimension(392, 500));
            setLayout(new BorderLayout());

            JPanel inPanel = new JPanel();
            inPanel.setPreferredSize(new Dimension(352, 426));
            inPanel.setBackground(new Color(0,0,0,0));
            inPanel.setLayout(new BoxLayout(inPanel, BoxLayout.Y_AXIS));

            opponentSpace = new JPanel();
            opponentSpace.setBorder(BorderFactory.createLineBorder(Color.black));
            opponentSpace.setBackground(Color.WHITE);
            opponentSpace.setMaximumSize(new Dimension(352, 142));
            opponentSpace.setMinimumSize(new Dimension(352, 142));
            opponentSpace.setPreferredSize(new Dimension(352, 142));
            opponentSpace.setLayout(new FlowLayout(FlowLayout.LEADING, 15,15));

            //opponentCardStack.setBorder(BorderFactory.createLineBorder(Color.black));
            opponentCardStack.setPreferredSize(new Dimension(250,110));

            opponentTotal = new JLabel();
            opponentTotal.setFont(new Font("Veranda",1,28));
            opponentTotal.setText(Integer.toString(opponentPile.getBlackjackTotal()));

            opponentSpace.add(opponentCardStack);
            opponentSpace.add(opponentTotal);

            JPanel middle = new JPanel();
            middle.setBackground(new Color(0,0,0,0));
            middle.setPreferredSize(new Dimension(352, 70));
            middle.setLayout(new BorderLayout());
            middle.add(status, BorderLayout.CENTER);

            playerSpace = new JPanel();
            playerSpace.setBorder(BorderFactory.createLineBorder(Color.black));
            playerSpace.setBackground(Color.WHITE);
            playerSpace.setMaximumSize(new Dimension(352, 142));
            playerSpace.setMinimumSize(new Dimension(352, 142));
            playerSpace.setPreferredSize(new Dimension(352, 142));
            playerSpace.setLayout(new FlowLayout(FlowLayout.LEADING, 15,15));

            //playerCardStack.setBorder(BorderFactory.createLineBorder(Color.black));
            playerCardStack.setPreferredSize(new Dimension(250,110));

            playerTotal = new JLabel();
            playerTotal.setFont(new Font("Veranda",1,28));
            playerTotal.setText(Integer.toString(playerPile.getBlackjackTotal()));

            playerSpace.add(playerCardStack);
            playerSpace.add(playerTotal);

            inPanel.add(opponentSpace);
            inPanel.add(middle);
            inPanel.add(playerSpace);

            JPanel top = new JPanel();
            top.setBackground(new Color(0,0,0,0));
            top.setPreferredSize(new Dimension(392, 37));

            JPanel bot = new JPanel();
            bot.setBackground(new Color(0,0,0,0));
            bot.setPreferredSize(new Dimension(392, 37));

            add(inPanel, BorderLayout.CENTER);
            add(top, BorderLayout.NORTH);
            add(bot, BorderLayout.SOUTH);

        }

        public void refreshOpponent(){
            SwingUtilities.invokeLater(() -> {
                opponentTotal.setText("Other:\n" + Integer.toString(opponentPile.getBlackjackTotal()));
                opponentCardStack.removeAll();
                int i = 0;
                for (String card : opponentPile.pileAsStrings()){
                    CardImage tmp = new CardImage(card.charAt(0), card.charAt(1), i);
                    tmp.setBounds(i * 25, 0, 65,100);
                    opponentCardStack.add(tmp, new Integer(i*100));
                    i++;
                }
                self.validate();
                self.repaint();
            });
        }

        public void refreshPlayer(){
            SwingUtilities.invokeLater(() -> {
                playerTotal.setText("You:\n" + Integer.toString(playerPile.getBlackjackTotal()));
                playerCardStack.removeAll();
                int i = 0;
                for (String card : playerPile.pileAsStrings()){
                    CardImage tmp = new CardImage(card.charAt(0), card.charAt(1), i);
                    tmp.setBounds(i * 25, 0, 65,100);
                    playerCardStack.add(tmp,new Integer(i*100));
                    i++;
                }
                self.validate();
                self.repaint();
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(bgImage, 0, 0, null);
        }

    }

}
