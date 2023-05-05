package org.polimi.messages;

public class ChosenColumnMessage extends Message{
    private int column;
    public ChosenColumnMessage(String username, int column){
        super(username, MessageType.CHOSEN_COLUMN_REPLY);
        this.column = column;
    }

    public int getColumn(){
        return column;
    }
}
