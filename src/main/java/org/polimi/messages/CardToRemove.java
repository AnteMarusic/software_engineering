package org.polimi.messages;

import org.polimi.server.model.Coordinates;

import java.util.ArrayList;
import java.util.List;

public class CardToRemove extends Message{
    List<Coordinates> coordinates;
    List<Coordinates> toUpdateToPickable;
    public CardToRemove(String username, List<Coordinates> coordinates, List<Coordinates> toUpdateToPickable){
        super(username, MessageType.CARD_TO_REMOVE);
        this.toUpdateToPickable = toUpdateToPickable;
        this.coordinates = coordinates;
    }
}
