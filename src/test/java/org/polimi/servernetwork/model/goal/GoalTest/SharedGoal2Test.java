package org.polimi.servernetwork.model.goal.GoalTest;

import org.junit.jupiter.api.Test;
import org.polimi.servernetwork.model.Card;
import org.polimi.servernetwork.model.goal.Goal;
import org.polimi.servernetwork.model.goal.shared_goal.SharedGoal2;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SharedGoal2Test {
    @Test
    void expectedTrue(){
        Card[][] grid = new Card[6][5];
        grid[0][0] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[0][4] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[5][0] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[5][4] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        Goal goal = new SharedGoal2(2);
        assertEquals(4, goal.getScore(grid));
    }

    @Test
    void expectedFalse(){
        Card[][] grid = new Card[6][5];
        grid[0][0] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[0][4] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[5][0] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[5][4] = new Card(Card.Color.BLUE, Card.State.PICKABLE);
        Goal goal = new SharedGoal2(2);
        assertEquals(0, goal.getScore(grid));
    }
}
