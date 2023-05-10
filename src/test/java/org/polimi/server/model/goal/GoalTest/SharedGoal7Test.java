package org.polimi.server.model.goal.GoalTest;

import org.junit.jupiter.api.Test;
import org.polimi.server.model.Card;
import org.polimi.server.model.goal.Goal;
import org.polimi.server.model.goal.shared_goal.SharedGoal6;
import org.polimi.server.model.goal.shared_goal.SharedGoal7;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SharedGoal7Test {
    @Test
    void expectedTrue(){
        Card[][] grid = new Card[6][5];
        grid[0][0] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[1][1] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[2][2] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[3][3] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[4][4] = new Card(Card.Color.WHITE, Card.State.PICKABLE);

        Goal goal = new SharedGoal7(2);
        assertEquals(4, goal.getScore(grid));
    }

    @Test
    void expectedFalse(){
        Card[][] grid = new Card[6][5];
        grid[0][0] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[1][1] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[2][2] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[3][3] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[4][4] = new Card(Card.Color.BLUE, Card.State.PICKABLE);

        Goal goal = new SharedGoal7(2);
        assertEquals(0, goal.getScore(grid));
    }
}
