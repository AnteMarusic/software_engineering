package org.polimi;

import org.polimi.personal_goal.PersonalGoal;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Bookshelf bookshelf = new Bookshelf();
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(Card.Color.CYAN, Card.State.PICKABLE));
        cards.add(new Card(Card.Color.WHITE, Card.State.PICKABLE));
        cards.add(new Card(Card.Color.ORANGE, Card.State.PICKABLE));
        bookshelf.insert(cards, 0);
        bookshelf.print();
    }
}