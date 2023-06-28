package org.polimi.messages;

public class CurrentScore extends Message{
    private int currentScore;
    public CurrentScore (String username, int currentScore){
        super(username, MessageType.CURRENT_SCORE);
        this.currentScore = currentScore;
    }

    public int getCurrentScore(){
        return this.currentScore;
    }
}
