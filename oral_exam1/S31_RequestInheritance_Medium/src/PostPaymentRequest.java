import types.Payment;

import java.util.UUID;

/**
 * PostPaymentRequest extends the PostRequest class and adds it's own instance
 * count, as well as a local payment object.
 *
 * @see PostRequest
 */
public class PostPaymentRequest extends PostRequest {

    private Payment payment;
    private static int postPaymentRequestCount;

    /**
     * A constructor that acts as an initial setter of uuid, ip, and payment.
     * uuid and ip are forwarded to the parent constructor, and the payment is
     * stored locally.
     *
     * @param requestUUID id to be passed to the PostRequest constructor
     * @param ip          ip address to be passed to the PostRequest constructor
     * @param payment     internalize a payment object to represent survey results
     */
    public PostPaymentRequest(UUID requestUUID, String ip, Payment payment) {
        super(requestUUID, ip);
        this.payment = payment;
        postPaymentRequestCount += 1;
    }

    @Override
    public String toString() {
        return super.toString() + "\n" + payment.toString();
    }

    /**
     * getter for the static count of PostPaymentRequest objects currently instantiated
     *
     * @return count of PostPaymentRequest objects currently instantiated
     */
    public static int count() {
        return postPaymentRequestCount;
    }
}
