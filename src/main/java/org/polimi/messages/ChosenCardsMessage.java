package org.polimi.messages;

import org.polimi.server.model.Coordinates;

import java.util.ArrayList;
import java.util.List;

public class ChosenCardsMessage extends Message{
    private List<Coordinates> coordinates;

    public ChosenCardsMessage(String username, List<Coordinates> coordinates) {
        super(username, MessageType.CHOSEN_CARDS_REPLY);
        this.coordinates = coordinates;
    }

    public List<Coordinates> getCards() {
        return coordinates;
    }

    @Override
    public String toString() {
        return super.toString() + "choosen cards";
    }
}
