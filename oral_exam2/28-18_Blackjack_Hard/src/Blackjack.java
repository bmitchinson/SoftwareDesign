import javax.swing.*;

/**
 * The BlackJack class instantiates a Dealer object that functions as a server,
 * and two client objects who face one another. This class initializes them and
 * provides them with connection, but from that point on, all gameplay is
 * appropriately controlled by the Dealer to avoid user tampering.
 *
 * This module is an implementation of the assignment: 28-18_Blackjack_Hard
 *
 * @author Ben Mitchinson
 * @see #main(String[])
 * @since 11/17/2018
 */
public class Blackjack {
    public static void main(String[] args){
        Client clientOne = new Client(1);
        Client clientTwo = new Client(2);
        Dealer dealer = new Dealer();
        clientOne.runClient();
        clientTwo.runClient();
        dealer.playGame();
        //clientOne.closeConnection();
        System.exit(0);
    }
    // holds terminal dealer server
    // holds 2 players

    // Variables:
    // Array of players, dealer at position 0

    // Methods:
    // intro() - Fancy game intro text, poker table?
    // setup() - how many players + init dealer, init them call dealer deal for
    //           each player: ( players[0].initDeal(dealer.deal(2)) )
    // play() - iterate through players asking if hit or stay
    // exit() thank you for playing

    // steps:
    // call blackjack.intro()
    // call blackjack.setup()
    // call blackjack.play()
}
