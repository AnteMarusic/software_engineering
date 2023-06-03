package org.polimi.messages;

import java.io.Serializable;

public class DisconnectionAlert extends Message implements Serializable {
    String usernameDisconnected;
    public DisconnectionAlert(String username, String usernamedisconnected){
        super(username, MessageType.DISCONNECTION_ALLERT);
        this.usernameDisconnected = usernamedisconnected;
    }
    public String getUsernameDisconnected(){
        return usernameDisconnected;
    }

}
