/**
 * The VigenereMessage class holds a message and it's state (encrypted / decrypted),
 * and provides methods to the user to change it's state using the key located in
 * a local file: "key.txt"
 */
public class VigenereMessage {

    private String message;
    private boolean messageIsEncrypted;

    public VigenereMessage(String message){
        this.message = message;
        this.messageIsEncrypted = false;
    }

    public void encrypt(){
        String key = "abc"; // TODO: Read key from file instead
        char[] newMessage = new char[message.length()];
        int i=0;
        for(char letter : message.toCharArray()){
            if (letter == ' '){
                newMessage[i] = ' ';
            }
            else{
                int letterNum = (char)letter - 96;
                int shift = (char)key.toCharArray()[i%key.length()]-96;
                newMessage[i] = (char)(((letterNum + shift)%26)+96);
            }
            i++;
        }
        message = new String(newMessage);
        messageIsEncrypted = true;
        System.out.println("Message encrypted using key: " + key);
        System.out.println(toString());
    }

    public void decrypt(){
        String key = "abc"; // TODO: Read key from file

        char[] newMessage = new char[message.length()];
        int i=0;

        for(char letter : message.toCharArray()){
            if (letter == ' '){
                newMessage[i] = ' ';
            }
            else{
                int letterNum = (char)letter - 96;
                int shift = (char)key.toCharArray()[i%key.length()]-96;
                int newNum = (letterNum - shift);
                while (newNum <= 0){
                    newNum += 26;
                }
                newMessage[i] = (char)(newNum+96);
            }
            i++;
        }

        message = new String(newMessage);
        messageIsEncrypted = false;
        System.out.println("Message decrypted using key: " + key);
        System.out.println(toString());
    }

    public String getMessage(){
        return message;
    }

    @Override
    public String toString(){
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
