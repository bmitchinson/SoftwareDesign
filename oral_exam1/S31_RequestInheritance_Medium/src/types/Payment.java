package types;

/**
 * Class to represent Payment information. Holds Strings for sender and receiver,
 * and an int for the payment amount.
 */
public class Payment {
    String sender, receiver;
    int amount;

    /**
     * Constructor acts as an initial setter for the sender, amount, and receiver
     *
     * @param sender   the name of who sent the payment
     * @param amount   the dollar amount of the transaction
     * @param receiver the name of who received the payment
     */
    public Payment(String sender, int amount, String receiver) {
        this.sender = sender;
        this.amount = amount;
        this.receiver = receiver;
    }

    @Override
    public String toString() {
        String output = "Payment Data\n";
        output += "Payment sender: " + sender + "\nPayment amount: $" + amount
                + "\nPayment receiver: " + receiver;
        return output;
    }
}
