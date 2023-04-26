package org.polimi.messages;

public class TextMessage extends Message{
    private final String text;

    public TextMessage (String username, MessageType messageType, String text) {
        super (username, messageType);
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