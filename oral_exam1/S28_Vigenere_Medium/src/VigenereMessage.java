import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * The VigenereMessage class holds a message and it's state (encrypted / decrypted),
 * and provides methods to the user to change it's state using the key located in
 * a local file: "key.txt"
 */
public class VigenereMessage {

    private String message;
    private boolean messageIsEncrypted;

    /**
     * Initialize the VigenereMessage object with a message, and set it to
     * unencrypted by default.
     *
     * @param message The message to later encrypt / decrypt in main.
     * @see Vigenere
     */
    public VigenereMessage(String message) {
        this.message = message;
        this.messageIsEncrypted = false;
    }

    /**
     * Encrypt the local message using the string key stored in key.txt
     */
    public void encrypt() {
        File inputFile = new File("oral_exam1/S28_Vigenere_Medium/key.txt");
        String key = null;
        try {
            Scanner inputFileScanner = new Scanner(inputFile);
            key = inputFileScanner.nextLine();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        char[] newMessage = new char[message.length()];
        int i = 0;
        for (char letter : message.toCharArray()) {
            if (letter == ' ') {
                newMessage[i] = ' ';
            } else {
                int letterNum = (char) letter - 96;
                int shift = (char) key.toCharArray()[i % key.length()] - 96;
                newMessage[i] = (char) (((letterNum + shift) % 26) + 96);
            }
            i++;
        }
        message = new String(newMessage);
        messageIsEncrypted = true;
        System.out.println("Message encrypted using key: " + key);
        System.out.println(toString());
    }

    /**
     * Decrypt the currently encrypted message using the string key value stored
     * in key.txt
     */
    public void decrypt() {
        File inputFile = new File("oral_exam1/S28_Vigenere_Medium/key.txt");
        String key = null;
        try {
            Scanner inputFileScanner = new Scanner(inputFile);
            key = inputFileScanner.nextLine();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        char[] newMessage = new char[message.length()];
        int i = 0;

        for (char letter : message.toCharArray()) {
            if (letter == ' ') {
                newMessage[i] = ' ';
            } else {
                int letterNum = (char) letter - 96;
                int shift = (char) key.toCharArray()[i % key.length()] - 96;
                int newNum = (letterNum - shift);
                while (newNum <= 0) {
                    newNum += 26;
                }
                newMessage[i] = (char) (newNum + 96);
            }
            i++;
        }

        message = new String(newMessage);
        messageIsEncrypted = false;
        System.out.println("Message decrypted using key: " + key);
        System.out.println(toString());
    }

    /**
     * Getter for local message
     *
     * @return
     */
    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        String output = "\n";
        // File name
        output += "Vigenere Message Status:\n";
        String status = (messageIsEncrypted) ? ("encrypted") : ("decrypted");
        output += "The message is currently " + status + "\n";
        output += "Your message says: \"" + message + "\"\n";
        // Encryption Key
        return output;
    }

}
