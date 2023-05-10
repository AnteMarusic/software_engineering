package org.polimi.server.model.goal.GoalTest;

import org.junit.jupiter.api.Test;
import org.polimi.server.model.Card;
import org.polimi.server.model.goal.Goal;
import org.polimi.server.model.goal.shared_goal.SharedGoal4;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SharedGoal4Test {
    @Test
    void expectedTrue(){
        Card[][] grid = new Card[6][5];
        grid[0][0] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[0][1] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[1][1] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[1][0] = new Card(Card.Color.WHITE, Card.State.PICKABLE);

        grid[0][4] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[0][3] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[1][4] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[1][3] = new Card(Card.Color.WHITE, Card.State.PICKABLE);

        Goal goal = new SharedGoal4(2);
        assertEquals(4, goal.getScore(grid));
    }

    @Test
    void expectedFalse(){
        Card[][] grid = new Card[6][5];
        grid[0][0] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[0][1] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[1][1] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[1][0] = new Card(Card.Color.WHITE, Card.State.PICKABLE);

        grid[0][1] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[0][2] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[1][1] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[1][2] = new Card(Card.Color.WHITE, Card.State.PICKABLE);

        Goal goal = new SharedGoal4(2);
        assertEquals(0, goal.getScore(grid));
    }
}
