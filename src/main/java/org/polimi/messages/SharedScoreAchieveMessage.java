package org.polimi.messages;

public class SharedScoreAchieveMessage extends Message{
    int newPoints;
    int index;

    public SharedScoreAchieveMessage ( int index, int newPoints){
        super("server", MessageType.SHAREDSCOREACHIEVE_MESSAGE);
        this.index = index;
        this.newPoints = newPoints;
    }

    public int getIndex(){
        return this.index;
    }
    public int getNewPoints(){
        return this.newPoints;
    }
}
