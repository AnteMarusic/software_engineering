package org.polimi.messages;

public class ChosenColumnReply extends Message{
    private int column;
    public ChosenColumnReply(String username, int column){
        super(username, MessageType.CHOSEN_COLUMN_REPLY);


    }
}
