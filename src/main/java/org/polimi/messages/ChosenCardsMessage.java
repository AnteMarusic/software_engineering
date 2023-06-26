package org.polimi.messages;

import org.polimi.servernetwork.model.Card;
import org.polimi.servernetwork.model.Coordinates;

import java.io.Serializable;
import java.util.List;

public class ChosenCardsMessage extends Message implements Serializable {
    private List<Coordinates> coordinates;

    private List<Card> cards;

    public ChosenCardsMessage(String username, List<Coordinates> coordinates, List<Card> cards) {
        super(username, MessageType.CHOSEN_CARDS_REPLY);
        this.coordinates = coordinates;
        this.cards = cards;
    }
    public ChosenCardsMessage(String username, List<Coordinates> coordinates) {
        super(username, MessageType.CHOSEN_CARDS_REPLY);
        this.coordinates = coordinates;
    }

    public List<Coordinates> getCoordinates() {
        return coordinates;
    }

    public List<Card> getCards(){return this.cards;}

    @Override
    public String toString() {
        return super.toString() + "chosen cards" + coordinates + "chosenCards size" + coordinates.size();
    }
}
