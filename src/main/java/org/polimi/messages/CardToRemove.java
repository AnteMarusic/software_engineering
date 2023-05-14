package org.polimi.messages;

import org.polimi.server.model.Coordinates;

import java.util.ArrayList;
import java.util.List;

public class CardToRemove extends Message{
    List<Coordinates> coordinates;
    public CardToRemove(String username, List<Coordinates> coordinates){
        super(username, MessageType.CARD_TO_REMOVE);
        this.coordinates = coordinates;
    }
}
