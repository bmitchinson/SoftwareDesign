import types.Payment;

import java.util.UUID;

/**
 * PostEncryptedPaymentRequest is an extension of the PostPaymentRequest class
 * that adds a String to represent Encryption type, as well as it's own instance
 * counter
 *
 * @see PostPaymentRequest
 */
public class PostEncryptedPaymentRequest extends PostPaymentRequest{

    private String encryption;
    static private int postEncryptedPaymentRequestCount;

    /**
     * The constructor acts as a setter for uuid, ip, payment, and encryption, by
     * forwarding uuid, ip and payment along to the parent constructor, while storing
     * the encryption string.
     *
     * @param uuid unique id to represent the request
     * @param ip address to represent request origin
     * @param payment object to payment transaction information
     * @param encryption type of encryption
     */
    public PostEncryptedPaymentRequest(UUID uuid, String ip, Payment payment, String encryption) {
        super(uuid, ip, payment);
        this.encryption = encryption;
        postEncryptedPaymentRequestCount += 1;
    }

    @Override
    public String toString() {
        return super.toString() + "\nThis payment was encrypted using: " + encryption;
    }

    /**
     * getter for the number of PostEncryptedPaymentRequest objects currently
     * instantiated
     *
     * @return number of PostEncryptedPaymentRequest objects currently
     * instantiated
     */
    public static int count() {
        return postEncryptedPaymentRequestCount;
    }

}
