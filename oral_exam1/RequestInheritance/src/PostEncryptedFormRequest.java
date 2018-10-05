import types.Form;

import java.util.UUID;

/**
 * PostEncryptedFormRequest is an extension of the PostFormRequest class that adds
 * a String to represent Encryption type, as well as it's own instance counter.
 *
 * @see PostFormRequest
 */
public class PostEncryptedFormRequest extends PostFormRequest {
    private String encryption;
    static private int postEncryptedFormRequestCount;

    /**
     * The constructor acts as a setter for uuid, ip, form, and encryption, by
     * forwarding uuid, ip and form along to the parent constructor, while storing
     * the encryption string.
     *
     * @param uuid unique id to represent the request
     * @param ip address to represent request origin
     * @param form object to represent form questions mapped to their responses
     * @param encryption type of encryption
     */
    public PostEncryptedFormRequest(UUID uuid, String ip, Form form, String encryption) {
        super(uuid, ip, form);
        this.encryption = encryption;
        postEncryptedFormRequestCount += 1;
    }

    @Override
    public String toString() {
        return super.toString() + "\nThis form was encrypted using: " + encryption;
    }

    /**
     * getter for the number of PostEncryptedFormRequest objects currently instantiated
     *
     * @return number of PostEncryptedFormRequest objects currently instantiated
     */
    public static int count() {
        return postEncryptedFormRequestCount;
    }

}
