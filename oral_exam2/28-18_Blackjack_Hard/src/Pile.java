public class Pile {

    private Card[] cardsInPile;
    // Hearts, Diamonds, Spades, Clubs
    private char[] suits = {'H','D','S','C'};
    //T means 10
    private char[] values = {'A','2','3','4','5','6','7','8','9','T','K','Q','J'};
    private int insertIndex = 0;

    public void Pile(Boolean deckMode){
        cardsInPile = new Card[52];
        if (deckMode){
            System.out.println("Initializing a full Deck");
            for (char suit : suits){
                for (char value : values){
                    cardsInPile[insertIndex] = new Card(suit, value);
                    insertIndex++;
                }
            }
            printPile();
        }
    }

    public void printPile(){
        int i = 0;
        System.out.println("Front beginning to end of pile:");
        for (Card card : cardsInPile){
            System.out.println("Pos:"+i+
                    " S:"+card.getSuit()+
                    " V:"+card.getValue());
        }
    }

    public void addToPile(Card[] cardsToAdd){
        for (Card card : cardsToAdd){
            cardsInPile[insertIndex] = card;
            insertIndex++;
        }
    }

    public void addToPile(Card cardToAdd){
        Card[] singleCardArray = {cardToAdd};
        addToPile(singleCardArray);
    }

}

// TODO: Create a "Hand" class to represent cards, but also their total.
//       Dynamically factors in the ace based on what's held. Constructed with
//       an initial deal. Use this instead of the Card array below.