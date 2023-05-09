package org.polimi.server.model.goal.GoalTest;

import org.junit.jupiter.api.Test;
import org.polimi.server.model.Card;
import org.polimi.server.model.goal.Goal;
import org.polimi.server.model.goal.shared_goal.SharedGoal10;
import org.polimi.server.model.goal.shared_goal.SharedGoal11;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SharedGoal11Test {
    @Test
    void expectedTrue(){
        Card[][] grid = new Card[6][5];
        grid[2][2] = new Card(Card.Color.ORANGE, Card.State.PICKABLE);
        grid[3][1] = new Card(Card.Color.ORANGE, Card.State.PICKABLE);
        grid[1][3] = new Card(Card.Color.ORANGE, Card.State.PICKABLE);
        grid[1][1] = new Card(Card.Color.ORANGE, Card.State.PICKABLE);
        grid[3][3] = new Card(Card.Color.ORANGE, Card.State.PICKABLE);


        Goal goal = new SharedGoal11(2);
        assertEquals(4, goal.getScore(grid));
    }

    @Test
    void expectedFalse(){
        Card[][] grid = new Card[6][5];
        grid[2][2] = new Card(Card.Color.ORANGE, Card.State.PICKABLE);
        grid[3][1] = new Card(Card.Color.ORANGE, Card.State.PICKABLE);
        grid[1][3] = new Card(Card.Color.ORANGE, Card.State.PICKABLE);
        grid[1][1] = new Card(Card.Color.ORANGE, Card.State.PICKABLE);
        grid[3][3] = new Card(Card.Color.BLUE, Card.State.PICKABLE);

        Goal goal = new SharedGoal11(2);
        assertEquals(0, goal.getScore(grid));
    }
}
