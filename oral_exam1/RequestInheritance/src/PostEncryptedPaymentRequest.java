import types.Payment;

import java.util.UUID;

public class PostEncryptedPaymentRequest extends PostPaymentRequest{

    String encryption;
    static private int postEncryptedPaymentRequestCount;

    public PostEncryptedPaymentRequest(UUID uuid, String ip, Payment payment, String encryption) {
        super(uuid, ip, payment);
        this.encryption = encryption;
        postEncryptedPaymentRequestCount += 1;
    }

    @Override
    public String toString() {
        return super.toString() + "\nThis payment was encrypted using: " + encryption;
    }

    public static int count() {
        return postEncryptedPaymentRequestCount;
    }

}
