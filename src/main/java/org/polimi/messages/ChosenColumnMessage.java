package org.polimi.messages;

import java.io.Serializable;

public class ChosenColumnMessage extends Message implements Serializable {
    private int column;
    public ChosenColumnMessage(String username, int column){
        super(username, MessageType.CHOSEN_COLUMN_REPLY);
        this.column = column;
    }

    public int getColumn(){
        return column;
    }

    @Override
    public String toString() {
        return "ChosenColumnMessage{" +
                "column=" + column +
                '}';
    }
}
