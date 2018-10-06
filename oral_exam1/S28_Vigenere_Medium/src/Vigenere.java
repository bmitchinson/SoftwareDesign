import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

/**
 * Vigenere acts as a 'driver' class to guide the user through interaction with a
 * VigenereMessage object. It also validates input, as only a-z can be encrypted/
 * decrypted, and makes the appropriate calls to write the cipher key to a local
 * file output.
 */
public class Vigenere {

    /**
     * The Vigenere main method asks for and validates a message to be encrypted from
     * the user, creates a VigenereMessage object using that message, saves a key
     * to a local file to use, encrypts the file, then prompts to decrypt the file,
     * and exits.
     *
     * @param args unused
     * @see VigenereMessage
     */
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        boolean valid = false;

        System.out.println("********* Welcome to Ben's Vigenere Message Handler *********\n");

        // Ask for a message to convert and validate that it's lowercase letters or spaces
        System.out.println("What message would you like to encrypt?");
        String message = null;
        while (!valid) {
            System.out.println("(Please use only lowercase values of a-z and spaces.)");
            System.out.print("Message:");
            message = input.nextLine();
            //message.replaceAll("[\n\r]", "");
            char[] messageArray = message.toCharArray();
            for (int i = 0; i < message.length(); i++) {
                int letter = (int) messageArray[i] - 96;
                if (letter <= 0 || letter > 26) {
                    i = message.length();
                    valid = false;
                } else {
                    valid = true;
                }
            }
        }

        // Create the VigenereMessage object out of the entered and approved message
        VigenereMessage vMessage = new VigenereMessage(message);
        System.out.println(vMessage.toString());

        // Ask the user to enter a key value, validate it, and write it to file.
        System.out.println("What key would you like to use to encrypt your message?");
        System.out.println("(Your key will be saved in \"key.txt\")");
        String key = null;
        valid = false;
        while (!valid) {
            System.out.println("(Please use only lowercase values of a-z and spaces.)");
            System.out.print("Key:");
            key = input.nextLine();
            //key.replaceAll("[\n\r]", "");
            char[] keyArray = key.toCharArray();
            for (int i = 0; i < key.length(); i++) {
                if ((int) keyArray[i] - 96 <= 0 || (int) keyArray[i] - 96 > 26) {
                    valid = false;
                    i = key.length();
                } else {
                    valid = true;
                }
            }
        }

        // Write to file using PrintWriter
        try {
            PrintWriter keyFile = new PrintWriter("oral_exam1/S28_Vigenere_Medium/key.txt", "UTF-8");
            keyFile.write(key);
            keyFile.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println("Key saved!");

        // Prompt the user to call the encrypt method on their VigenereMessage object.
        //     This method will use the file saved from earlier.
        System.out.print("\nPress enter to use the key from \"key.txt\" and encrypt your message:");
        input.nextLine();
        vMessage.encrypt();

        // Prompt the user to call the decrypt method on their VigenereMessage object.
        //     This method will use the file saved from earlier.
        System.out.print("Press enter to decrypt your message using \"key.txt\"");
        input.nextLine();
        vMessage.decrypt();

        // End
        System.out.println("********* End of Demo, Thank You. *********");
        System.exit(0);
    }
}
