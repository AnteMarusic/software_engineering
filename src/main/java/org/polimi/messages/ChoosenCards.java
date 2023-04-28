package org.polimi.messages;

import org.polimi.server.model.Card;

import java.util.ArrayList;

public class ChoosenCards extends Message{
    private ArrayList<Card> cards;

    public ChoosenCards (String username, ArrayList<Card> cards) {
        super(username, MessageType.CHOOSEN_CARDS);
        this.cards = cards;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    @Override
    public String toString() {
        return super.toString() + "choosen cards";
    }
}
