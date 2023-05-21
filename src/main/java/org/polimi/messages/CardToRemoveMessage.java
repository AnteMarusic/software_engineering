package org.polimi.messages;

import org.polimi.servernetwork.model.Coordinates;

import java.util.List;

public class CardToRemoveMessage extends Message{
    private List<Coordinates> coordinates;
    public CardToRemoveMessage(String username, List<Coordinates> coordinates){
        super(username, MessageType.CARD_TO_REMOVE);
        this.coordinates = coordinates;
    }
    public List<Coordinates> getCoordinates(){
        return coordinates;
    }
}
