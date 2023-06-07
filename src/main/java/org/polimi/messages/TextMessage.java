package org.polimi.messages;

import java.io.Serializable;

public class TextMessage extends Message implements Serializable {
    private final String text;

    public TextMessage (String username, String text) {
        super (username, MessageType.TEXT_MESSAGE);
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    @Override
    public String toString() {
        return super.toString() + text;
    }
}