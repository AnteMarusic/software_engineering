package org.polimi.servernetwork.model.goal.GoalTest;

import org.junit.jupiter.api.Test;
import org.polimi.servernetwork.model.Card;
import org.polimi.servernetwork.model.goal.Goal;
import org.polimi.servernetwork.model.goal.shared_goal.SharedGoal10;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SharedGoal10Test {
    @Test
    void expectedTrue(){
        Card[][] grid = new Card[6][5];
        grid[0][0] = new Card(Card.Color.ORANGE, Card.State.PICKABLE);
        grid[0][1] = new Card(Card.Color.BLUE, Card.State.PICKABLE);
        grid[0][2] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[0][3] = new Card(Card.Color.PINK, Card.State.PICKABLE);
        grid[0][4] = new Card(Card.Color.GREEN, Card.State.PICKABLE);

        grid[2][0] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[2][1] = new Card(Card.Color.BLUE, Card.State.PICKABLE);
        grid[2][2] = new Card(Card.Color.ORANGE, Card.State.PICKABLE);
        grid[2][3] = new Card(Card.Color.CYAN, Card.State.PICKABLE);
        grid[2][4] = new Card(Card.Color.PINK, Card.State.PICKABLE);

        Goal goal = new SharedGoal10(2);
        assertEquals(4, goal.getScore(grid));
    }

    @Test
    void expectedFalse(){
        Card[][] grid = new Card[6][5];
        grid[0][0] = new Card(Card.Color.ORANGE, Card.State.PICKABLE);
        grid[0][1] = new Card(Card.Color.BLUE, Card.State.PICKABLE);
        grid[0][2] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[0][3] = new Card(Card.Color.PINK, Card.State.PICKABLE);
        grid[0][4] = new Card(Card.Color.PINK, Card.State.PICKABLE);

        grid[2][0] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[2][1] = new Card(Card.Color.BLUE, Card.State.PICKABLE);
        grid[2][2] = new Card(Card.Color.ORANGE, Card.State.PICKABLE);
        grid[2][3] = new Card(Card.Color.GREEN, Card.State.PICKABLE);
        grid[2][4] = new Card(Card.Color.GREEN, Card.State.PICKABLE);

        Goal goal = new SharedGoal10(2);
        assertEquals(0, goal.getScore(grid));
    }
}
