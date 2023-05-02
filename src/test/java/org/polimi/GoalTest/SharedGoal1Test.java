package org.polimi.GoalTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.polimi.server.model.Bookshelf;
import org.polimi.server.model.Card;
import org.polimi.server.model.goal.shared_goal.SharedGoal1;

import static org.junit.jupiter.api.Assertions.*;

public class SharedGoal1Test {
    @Test
    void expectedTrue(){
        Card[][] grid = new Card[6][5];
        grid[5][0] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[4][0] = new Card(Card.Color.WHITE, Card.State.PICKABLE);

        grid[4][2] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[4][3] = new Card(Card.Color.WHITE, Card.State.PICKABLE);

        grid[2][4] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[3][4] = new Card(Card.Color.WHITE, Card.State.PICKABLE);

        grid[2][0] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[2][1] = new Card(Card.Color.WHITE, Card.State.PICKABLE);

        grid[0][0] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[0][1] = new Card(Card.Color.WHITE, Card.State.PICKABLE);

        grid[1][2] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[1][3] = new Card(Card.Color.WHITE, Card.State.PICKABLE);

        SharedGoal1 goal = new SharedGoal1(2);

        assertEquals(goal.getScore(grid), 4);
    }

    @Test
    void expectedFalse(){
        Card[][] grid = new Card[6][5];
        grid[5][0] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[4][0] = new Card(Card.Color.WHITE, Card.State.PICKABLE);

        grid[4][2] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[4][3] = new Card(Card.Color.WHITE, Card.State.PICKABLE);

        grid[2][4] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[3][4] = new Card(Card.Color.WHITE, Card.State.PICKABLE);

        grid[2][0] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[2][1] = new Card(Card.Color.WHITE, Card.State.PICKABLE);

        grid[0][0] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[0][1] = new Card(Card.Color.WHITE, Card.State.PICKABLE);

        grid[1][2] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[1][3] = new Card(Card.Color.BLUE, Card.State.PICKABLE);

        SharedGoal1 goal = new SharedGoal1(2);

        assertEquals(goal.getScore(grid), 0);
    }
}
