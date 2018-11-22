public class Card {
    private char suit;
    private char value;

    Card(char suit, char value){
        this.suit = suit;
        this.value = value;
    }

    public char getSuit(){ return suit; }

    public char getValue(){ return value; }

    public void printCard() {
        System.out.println("S:"+suit+" V:"+value);
    }

    // TODO: Get Card Representation as JFrame? getVisual.
    // Nah this would need to be on client side anyway unless I reconstructed a card

}