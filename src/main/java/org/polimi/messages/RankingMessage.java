package org.polimi.messages;

import java.util.Arrays;
import java.util.HashMap;

public class RankingMessage extends Message{
    private HashMap<String,Integer> gameRanking;
    public RankingMessage(String username, HashMap<String,Integer> ranking){
        super(username, MessageType.RANKING_MESSAGE );
        gameRanking = ranking;
    }
    @Override
    public String toString() {
        return super.toString() + "Game ranking" + "the other players are:" + gameRanking.toString();
    }
}
