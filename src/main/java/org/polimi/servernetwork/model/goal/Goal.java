package org.polimi.servernetwork.model.goal;
import org.polimi.servernetwork.model.Card;

public interface Goal {
    int getScore(Card[][] grid);
}
