package org.polimi.messages;


import java.io.Serializable;

public class Message implements Serializable {
    private String username;
    private MessageType messageType;

    public Message (String username) {
        this.username = username;
        this.messageType = MessageType.USERNAME;
    }

    public Message (String username, MessageType messageType) {
        this.username = username;
        this.messageType = messageType;
    }

    public String getUsername() {
        return username;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    @Override
    public String toString() {
        return messageType + ", " + username + " : ";
    }
}
