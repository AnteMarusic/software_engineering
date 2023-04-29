package org.polimi.messages;

import org.polimi.server.model.Coordinates;

import java.util.ArrayList;

public class ChosenCardsMessage extends Message{
    private ArrayList<Coordinates> coordinates;

    public ChosenCardsMessage(String username, ArrayList<Coordinates> coordinates) {
        super(username, MessageType.CHOSEN_CARDS);
        this.coordinates = coordinates;
    }

    public ArrayList<Coordinates> getCards() {
        return coordinates;
    }

    @Override
    public String toString() {
        return super.toString() + "choosen cards";
    }
}
