package org.polimi.servernetwork.model;

import java.util.*;

public class BagOfCards {
    private ArrayList<Card> bag;
    private Random random;

    public BagOfCards() {
        random = new Random();
        bag = new ArrayList<Card>(132);
        for(int i = 0; i < 22; i ++) {
            bag.add(new Card(Card.Color.CYAN, Card.State.IN_BAG, i%3));
        }
        for(int i = 0; i < 22; i ++) {
            bag.add(new Card(Card.Color.ORANGE, Card.State.IN_BAG,i%3));
        }
        for(int i = 0; i < 22; i ++) {
            bag.add(new Card(Card.Color.WHITE, Card.State.IN_BAG,i%3));
        }
        for(int i = 0; i < 22; i ++) {
            bag.add(new Card(Card.Color.GREEN, Card.State.IN_BAG,i%3));
        }
        for(int i = 0; i < 22; i ++) {
            bag.add(new Card(Card.Color.BLUE, Card.State.IN_BAG,i%3));
        }
        for(int i = 0; i < 22; i ++) {
            bag.add(new Card(Card.Color.PINK, Card.State.IN_BAG,i%3));
        }
    }

    public Card collectCard(){
        if (bag.size() == 0) {
            return null;
        }
        else {
            return bag.remove(random.nextInt(bag.size()));
        }
    }

    public int getSize(){
        return bag.size();
    }
}