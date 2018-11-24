import javax.swing.*;

public class StartClient implements Runnable {
    public static void main(String args[]){
        Client client = new Client();
        client.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void run(){
        Client client = new Client();
        client.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
