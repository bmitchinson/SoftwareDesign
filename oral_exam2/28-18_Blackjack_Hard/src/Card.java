public class Card {
    private char suit;
    private char value;

    Card(char suit, char value){
        this.suit = suit;
        this.value = value;
    }

    public char getSuit(){ return suit; }

    public char getValue(){ return value; }

    // TODO: Get Card Representation as JFrame?

}