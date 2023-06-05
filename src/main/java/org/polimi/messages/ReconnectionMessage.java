package org.polimi.messages;

import java.io.Serializable;

public class ReconnectionMessage extends Message implements Serializable {
    String enter;

    public ReconnectionMessage(String username, String enter){
        super(username, MessageType.RECONNECTION_MESSAGE);
        this.enter = enter;
    }
}
