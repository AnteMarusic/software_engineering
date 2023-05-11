package org.polimi.server.model.goal.shared_goal;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.polimi.server.model.Card;

import static org.junit.jupiter.api.Assertions.*;

class SharedGoal3Test {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void achievedTrue() {
        Card[][] grid = new Card[6][5];
        grid[0][0] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[0][1] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[0][2] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[0][3] = new Card(Card.Color.WHITE, Card.State.PICKABLE);

        grid[1][2] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[1][3] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[1][0] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[1][1] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[1][4] = new Card(Card.Color.WHITE, Card.State.PICKABLE);

        grid[2][0] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[2][1] = new Card(Card.Color.WHITE, Card.State.PICKABLE);

        grid[3][0] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[3][1] = new Card(Card.Color.WHITE, Card.State.PICKABLE);

        grid[4][0] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[4][1] = new Card(Card.Color.WHITE, Card.State.PICKABLE);

        grid[5][1] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        SharedGoal3 goal = new SharedGoal3(2);

        assertEquals(4, goal.getScore(grid));
    }
    @Test
    void achievedTru2() {
        Card[][] grid = new Card[6][5];
        grid[0][0] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[0][1] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[0][2] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[0][3] = new Card(Card.Color.WHITE, Card.State.PICKABLE);

        grid[1][2] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[1][3] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[1][0] = new Card(Card.Color.BLUE, Card.State.PICKABLE);
        grid[1][1] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[1][4] = new Card(Card.Color.WHITE, Card.State.PICKABLE);

        grid[2][0] = new Card(Card.Color.BLUE, Card.State.PICKABLE);
        grid[2][1] = new Card(Card.Color.WHITE, Card.State.PICKABLE);

        grid[3][0] = new Card(Card.Color.BLUE, Card.State.PICKABLE);
        grid[3][1] = new Card(Card.Color.WHITE, Card.State.PICKABLE);

        grid[4][0] = new Card(Card.Color.BLUE, Card.State.PICKABLE);
        grid[4][1] = new Card(Card.Color.WHITE, Card.State.PICKABLE);

        grid[5][1] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        SharedGoal3 goal = new SharedGoal3(2);

        assertEquals(4, goal.getScore(grid));
    }
    @Test
    void achievedTrue3() {
        Card[][] grid = new Card[6][5];
        grid[0][0] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[0][1] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[0][2] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[0][3] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[0][4] = new Card(Card.Color.WHITE, Card.State.PICKABLE);

        grid[1][0] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[1][1] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[1][2] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[1][3] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[1][4] = new Card(Card.Color.WHITE, Card.State.PICKABLE);


        grid[2][0] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[2][1] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[2][3] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[2][4] = new Card(Card.Color.WHITE, Card.State.PICKABLE);

        grid[3][0] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[3][1] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[3][3] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[3][4] = new Card(Card.Color.WHITE, Card.State.PICKABLE);

        SharedGoal3 goal = new SharedGoal3(2);

        assertEquals(4, goal.getScore(grid));
    }
    @Test
    void achievedFalse() {
        Card[][] grid = new Card[6][5];;
        SharedGoal3 goal = new SharedGoal3(2);
        assertEquals(0, goal.getScore(grid));
    }
    @Test
    void achievedFalse2() {
        Card[][] grid = new Card[6][5];;
        grid[0][1] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[0][2] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[0][3] = new Card(Card.Color.WHITE, Card.State.PICKABLE);

        grid[1][2] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[1][3] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[1][0] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[1][1] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[1][4] = new Card(Card.Color.WHITE, Card.State.PICKABLE);

        grid[2][0] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[2][1] = new Card(Card.Color.WHITE, Card.State.PICKABLE);

        grid[3][0] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[3][1] = new Card(Card.Color.WHITE, Card.State.PICKABLE);

        grid[4][0] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[4][1] = new Card(Card.Color.WHITE, Card.State.PICKABLE);

        grid[5][1] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        SharedGoal3 goal = new SharedGoal3(2);
        assertEquals(0, goal.getScore(grid));
    }
}