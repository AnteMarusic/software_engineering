package org.polimi.server.model.goal.GoalTest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.polimi.server.model.Card;
import org.polimi.server.model.goal.shared_goal.SharedGoal3;

import static org.junit.jupiter.api.Assertions.*;

class SharedGoal3Test {
    private Card[][] grid = new Card[6][5];
    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void achievedTrue() {
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
        grid[0][4] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[5][0] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        SharedGoal3 goal = new SharedGoal3(2);
        print();

        assertEquals(4, goal.getScore(grid));
    }

    private void print () {
        for (int i = 0; i < 6; i ++) {
            for (int j = 0; j < 5; j ++) {
                if (this.grid[i][j] == null) {
                    System.out.print("N");
                }
                else {
                    System.out.print(this.grid[i][j].convertColorToChar());
                }
            }
            System.out.println(" ");
        }
    }
    @Test
    void achievedTru2() {
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