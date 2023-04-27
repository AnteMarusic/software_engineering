package org.polimi.server.model.goal;
import org.polimi.server.model.Card;

public interface Goal {
    int getScore(Card[][] grid);
}
