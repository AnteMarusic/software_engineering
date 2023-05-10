package org.polimi.server.model.goal.GoalTest;

import org.junit.jupiter.api.Test;
import org.polimi.server.model.Card;
import org.polimi.server.model.goal.Goal;
import org.polimi.server.model.goal.shared_goal.SharedGoal8;
import org.polimi.server.model.goal.shared_goal.SharedGoal9;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SharedGoal9Test {
    @Test
    void expectedTrue(){
        Card[][] grid = new Card[6][5];
        grid[0][0] = new Card(Card.Color.ORANGE, Card.State.PICKABLE);
        grid[1][0] = new Card(Card.Color.BLUE, Card.State.PICKABLE);
        grid[2][0] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[3][0] = new Card(Card.Color.PINK, Card.State.PICKABLE);
        grid[4][0] = new Card(Card.Color.GREEN, Card.State.PICKABLE);
        grid[5][0] = new Card(Card.Color.CYAN, Card.State.PICKABLE);

        grid[5][2] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[4][2] = new Card(Card.Color.BLUE, Card.State.PICKABLE);
        grid[3][2] = new Card(Card.Color.ORANGE, Card.State.PICKABLE);
        grid[2][2] = new Card(Card.Color.CYAN, Card.State.PICKABLE);
        grid[1][2] = new Card(Card.Color.PINK, Card.State.PICKABLE);
        grid[0][2] = new Card(Card.Color.GREEN, Card.State.PICKABLE);

        Goal goal = new SharedGoal9(2);
        assertEquals(4, goal.getScore(grid));
    }

    @Test
    void expectedFalse(){
        Card[][] grid = new Card[6][5];
        grid[0][0] = new Card(Card.Color.ORANGE, Card.State.PICKABLE);
        grid[1][0] = new Card(Card.Color.BLUE, Card.State.PICKABLE);
        grid[2][0] = new Card(Card.Color.BLUE, Card.State.PICKABLE);
        grid[3][0] = new Card(Card.Color.PINK, Card.State.PICKABLE);
        grid[4][0] = new Card(Card.Color.GREEN, Card.State.PICKABLE);
        grid[5][0] = new Card(Card.Color.CYAN, Card.State.PICKABLE);

        grid[5][2] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[4][2] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[3][2] = new Card(Card.Color.ORANGE, Card.State.PICKABLE);
        grid[2][2] = new Card(Card.Color.CYAN, Card.State.PICKABLE);
        grid[1][2] = new Card(Card.Color.PINK, Card.State.PICKABLE);
        grid[0][2] = new Card(Card.Color.GREEN, Card.State.PICKABLE);

        Goal goal = new SharedGoal9(2);
        assertEquals(0, goal.getScore(grid));
    }
}
