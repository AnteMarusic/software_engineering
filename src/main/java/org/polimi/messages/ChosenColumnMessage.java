package org.polimi.messages;

import java.io.Serializable;

public class ChosenColumnMessage extends Message implements Serializable {
    private int column;
    private int player;
    public ChosenColumnMessage(String username, int column, int player){
        super(username, MessageType.CHOSEN_COLUMN_REPLY);
        this.column = column;
        this.player = player;
    }

    public int getColumn(){
        return column;
    }

    public int getPlayer() {
        return player;
    }

    @Override
    public String toString() {
        return "ChosenColumnMessage{" +
                "column=" + column +
                '}';
    }
}
