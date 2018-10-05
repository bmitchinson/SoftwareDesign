import types.Payment;

import java.util.UUID;

public class PostPaymentRequest extends PostRequest {

    private Payment payment;
    private static int postPaymentRequestCount;

    public PostPaymentRequest(UUID requestUUID, String ip, Payment payment) {
        super(requestUUID, ip);
        this.payment = payment;
        postPaymentRequestCount += 1;
    }

    @Override
    public String toString(){
        return super.toString() + "\n" + payment.toString();
    }

    public static int count(){
        return postPaymentRequestCount;
    }
}
