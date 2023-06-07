package org.polimi.servernetwork.model.goal.GoalTest;

import org.junit.jupiter.api.Test;
import org.polimi.servernetwork.model.Card;
import org.polimi.servernetwork.model.goal.Goal;
import org.polimi.servernetwork.model.goal.shared_goal.SharedGoal6;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SharedGoal6Test {
    @Test
    void expectedTrue(){
        Card[][] grid = new Card[6][5];
        grid[5][0] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[4][0] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[3][0] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[2][0] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[1][0] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[0][0] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[1][1] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[0][1] = new Card(Card.Color.WHITE, Card.State.PICKABLE);

        Goal goal = new SharedGoal6(2);
        assertEquals(4, goal.getScore(grid));
    }

    @Test
    void expectedFalse(){
        Card[][] grid = new Card[6][5];
        grid[5][0] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[4][0] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[3][0] = new Card(Card.Color.BLUE, Card.State.PICKABLE);
        grid[2][0] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[1][0] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[0][0] = new Card(Card.Color.WHITE, Card.State.PICKABLE);

        Goal goal = new SharedGoal6(2);
        assertEquals(0, goal.getScore(grid));
    }
}
