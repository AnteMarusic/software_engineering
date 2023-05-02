package org.polimi.GoalTest;

import org.junit.jupiter.api.Test;
import org.polimi.server.model.Card;
import org.polimi.server.model.goal.Goal;
import org.polimi.server.model.goal.shared_goal.SharedGoal2;

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
        assertEquals(goal.getScore(grid), 4);
    }

    @Test
    void expectedFalse(){
        Card[][] grid = new Card[6][5];
        grid[0][0] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[0][4] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[5][0] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[5][4] = new Card(Card.Color.BLUE, Card.State.PICKABLE);
        Goal goal = new SharedGoal2(2);
        assertEquals(goal.getScore(grid), 0);
    }
}
