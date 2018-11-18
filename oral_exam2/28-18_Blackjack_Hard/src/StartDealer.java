import javax.swing.*;

public class StartDealer {
    public static void main(String args[]){
        Dealer dealer = new Dealer();
        dealer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        dealer.execute();
    }
}
