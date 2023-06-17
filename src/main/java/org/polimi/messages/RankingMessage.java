package org.polimi.messages;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class RankingMessage extends Message implements Serializable {
    private Map<String,Integer> gameRanking;
    public RankingMessage(String username, Map<String,Integer> ranking){
        super(username, MessageType.RANKING_MESSAGE );
        gameRanking = ranking;
    }
    @Override
    public String toString() {
        return super.toString() + "Game ranking" + gameRanking.toString();
    }
}
