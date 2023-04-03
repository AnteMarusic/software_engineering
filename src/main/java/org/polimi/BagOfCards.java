package org.polimi;
import java.util.*;

public class BagOfCards {
    private List<Card> bag;
    private Random random;

    public BagOfCards() {
        random = new Random();
        bag = new ArrayList<Card>(132);
        for(int i = 0; i < 22; i ++) {
            bag.add(new Card(Card.Color.CYAN, Card.State.IN_BAG));
        }
        for(int i = 0; i < 22; i ++) {
            bag.add(new Card(Card.Color.ORANGE, Card.State.IN_BAG));
        }
        for(int i = 0; i < 22; i ++) {
            bag.add(new Card(Card.Color.WHITE, Card.State.IN_BAG));
        }
        for(int i = 0; i < 22; i ++) {
            bag.add(new Card(Card.Color.GREEN, Card.State.IN_BAG));
        }
        for(int i = 0; i < 22; i ++) {
            bag.add(new Card(Card.Color.BLUE, Card.State.IN_BAG));
        }
        for(int i = 0; i < 22; i ++) {
            bag.add(new Card(Card.Color.PINK, Card.State.IN_BAG));
        }
    }

    public Card collectCard(){
        Card c;
        if (bag.size() == 0) {
            return null;
        }
        else {
            int i = random.nextInt(bag.size());
            c = bag.get(random.nextInt(bag.size()));
            bag.remove(i);
            return c;
        }
    }
}