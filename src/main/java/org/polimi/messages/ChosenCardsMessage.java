package org.polimi.messages;

import org.polimi.servernetwork.model.Coordinates;

import java.io.Serializable;
import java.util.List;

public class ChosenCardsMessage extends Message implements Serializable {
    private List<Coordinates> coordinates;

    public ChosenCardsMessage(String username, List<Coordinates> coordinates) {
        super(username, MessageType.CHOSEN_CARDS_REPLY);
        this.coordinates = coordinates;
    }

    public List<Coordinates> getCoordinates() {
        return coordinates;
    }

    @Override
    public String toString() {
        return super.toString() + "chosen cards" + coordinates + "chosenCards size" + coordinates.size();
    }
}
