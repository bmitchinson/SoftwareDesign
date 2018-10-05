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
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);

        System.out.println("********* Welcome to Ben's Vigenere Message Handler *********\n");

        System.out.println("What message would you like to encrypt?");
        System.out.println("(Please use only lowercase values of a-z and spaces.)");
        System.out.print("Message:");
        String message = input.nextLine();
        // TODO: Validate
        VigenereMessage vMessage = new VigenereMessage(message);
        System.out.println(vMessage.toString());

        System.out.println("What key would you like to use to encrypt your message?");
        System.out.println("(Your key will be saved in \"key.txt\")");
        System.out.println("Please use only lowercase values of a-z and spaces.");
        System.out.print("Key:");
        String key = input.nextLine();
        try {
            PrintWriter keyFile = new PrintWriter("key.txt", "UTF-8");
            keyFile.write(key);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println("Key saved!");

        System.out.print("\nPress enter to use the key from \"key.txt\" and encrypt your message:");
        input.nextLine();
        vMessage.encrypt();

        System.out.print("Press enter to decrypt your message using \"key.txt\"");
        input.nextLine();
        vMessage.decrypt();

        System.out.println("********* End of Demo, Thank You. *********");
        System.exit(0);
    }
}
