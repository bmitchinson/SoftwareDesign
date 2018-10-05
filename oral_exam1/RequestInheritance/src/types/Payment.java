package types;

public class Payment {
    String sender, receiver;
    int amount;

    public Payment(String sender, int amount, String receiver){
        this.sender = sender;
        this.amount = amount;
        this.receiver = receiver;
    }
}
