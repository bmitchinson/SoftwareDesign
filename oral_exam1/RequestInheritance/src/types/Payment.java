package types;

public class Payment {
    String sender, receiver;
    int amount;

    public Payment(String sender, int amount, String receiver){
        this.sender = sender;
        this.amount = amount;
        this.receiver = receiver;
    }

    @Override
    public String toString(){
        String output = "Payment Data\n";
        output += "Payment sender: " + sender + "\nPayment amount: $" + amount
                + "\nPayment receiver: " + receiver;
        return output;
    }
}
