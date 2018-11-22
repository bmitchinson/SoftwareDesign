import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Pile {

    private Card[] cardsInPile;
    // Hearts, Diamonds, Spades, Clubs
    private char[] suits = {'H', 'D', 'S', 'C'};
    //T means 10
    private char[] values = {'A', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K'};
    private int insertIndex = 0;

    Pile(Boolean deckMode) {
        cardsInPile = new Card[52];
        if (deckMode) {
            for (char suit : suits) {
                for (char value : values) {
                    addToPile(new Card(suit, value));
                }
            }
            shufflePile();
        }
    }

    Pile(Card[] incomingCards) {
        cardsInPile = new Card[52];
        addToPile(incomingCards);
    }

    public void printPile() {
        int i = 0;
        for (Card card : cardsInPile) {
            if (card != null) {
                System.out.println("Pos:" + i +
                        " S:" + card.getSuit() +
                        " V:" + card.getValue());
                i++;
            }
        }
    }

    public String[] pileAsStrings() {
        String[] result = new String[insertIndex];
        for (int i = 0; i < insertIndex; i++) {
            result[i] = "" + cardsInPile[i].getSuit()
                    + cardsInPile[i].getValue();
        }
        return result;
    }

    public void shufflePile() {
        List<Card> temp = Arrays.asList(cardsInPile);
        Collections.shuffle(temp);
        cardsInPile = (Card[]) temp.toArray();
    }

    public void addToPile(Card[] cardsToAdd) {
        if (Array.getLength(cardsToAdd) + insertIndex > 52) {
            System.out.println("Cannot add cards, would overflow.");
        } else {
            for (Card card : cardsToAdd) {
                cardsInPile[insertIndex] = card;
                insertIndex++;
            }
        }
    }

    public void addToPile(Card cardToAdd) {
        Card[] singleCardArray = {cardToAdd};
        addToPile(singleCardArray);
    }

    public void addToPile(Pile pileToAdd) {
        String[] pileToAddAsString = pileToAdd.pileAsStrings();
        Card[] pileToAddAsCards = new Card[pileToAddAsString.length];
        int i = 0;
        for (String card : pileToAddAsString) {
            pileToAddAsCards[i] = new Card(card.charAt(0), card.charAt(1));
        }
        addToPile(pileToAddAsCards);
    }

    public Pile removeFromPile(int amount) {
        Card[] removed = new Card[amount];
        if (amount > insertIndex) {
            System.out.println(insertIndex + " Cards in pile, " +
                    "can't remove " + amount);
            return null;
        } else {
            for (int i = 0; i < amount; i++) {
                insertIndex--;
                removed[i] = cardsInPile[insertIndex];
                cardsInPile[insertIndex] = null;
            }
            Pile returnPile = new Pile(false);
            returnPile.addToPile(removed);
            return returnPile;
        }
    }

    public int getBlackjackTotal() {
        int total = 0;
        boolean hasAce = false;
        for (Card card : cardsInPile) {
            if (card != null) {
                char value = card.getValue();
                if (value == 'A') {
                    total += 11;
                    hasAce = true;
                } else if (value == 'T' || value == 'K' ||
                        value == 'Q' || value == 'J') {
                    total += 10;
                } else {
                    total += Character.getNumericValue(value);
                }
            }
        }
        if (hasAce && total > 21) {
            total -= 11;
        }
        return total;
    }
}