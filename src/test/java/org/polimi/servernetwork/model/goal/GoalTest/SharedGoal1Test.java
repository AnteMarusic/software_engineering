package org.polimi.servernetwork.model.goal.GoalTest;

import org.junit.jupiter.api.Test;
import org.polimi.servernetwork.model.Card;
import org.polimi.servernetwork.model.goal.shared_goal.SharedGoal1;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

        assertEquals(4, goal.getScore(grid));
    }

    @Test
    void expectedFalse1(){
        Card[][] grid = new Card[6][5];
        grid[5][0] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[4][0] = new Card(Card.Color.WHITE, Card.State.PICKABLE);

        grid[4][1] = new Card(Card.Color.BLUE, Card.State.PICKABLE);
        grid[5][1] = new Card(Card.Color.BLUE, Card.State.PICKABLE);

        grid[4][3] = new Card(Card.Color.BLUE, Card.State.PICKABLE);
        grid[5][3] = new Card(Card.Color.BLUE, Card.State.PICKABLE);


        SharedGoal1 goal = new SharedGoal1(2);

        assertEquals(0, goal.getScore(grid));
    }
    @Test
    void expectedFalse2(){
        Card[][] grid = new Card[6][5];
        grid[5][0] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[4][0] = new Card(Card.Color.WHITE, Card.State.PICKABLE);

        grid[2][4] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[3][4] = new Card(Card.Color.WHITE, Card.State.PICKABLE);

        grid[2][0] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[2][1] = new Card(Card.Color.WHITE, Card.State.PICKABLE);

        grid[0][0] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[0][1] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[1][1] = new Card(Card.Color.WHITE, Card.State.PICKABLE);

        grid[1][2] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[1][3] = new Card(Card.Color.WHITE, Card.State.PICKABLE);

        SharedGoal1 goal = new SharedGoal1(2);

        assertEquals(0, goal.getScore(grid));
    }
    @Test
    void expectedFalse3(){
        Card[][] grid = new Card[6][5];
        grid[0][4] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[1][4] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[2][4] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[3][4] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[4][4] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[5][4] = new Card(Card.Color.WHITE, Card.State.PICKABLE);

        grid[0][0] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[0][1] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[0][2] = new Card(Card.Color.WHITE, Card.State.PICKABLE);


        SharedGoal1 goal = new SharedGoal1(2);

        assertEquals(0, goal.getScore(grid));
    }
    @Test
    void expectedTrue2(){
        Card[][] grid = new Card[6][5];
        grid[0][4] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[1][4] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[2][4] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[3][4] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[4][4] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[5][4] = new Card(Card.Color.WHITE, Card.State.PICKABLE);

        grid[0][0] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[0][1] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[0][2] = new Card(Card.Color.WHITE, Card.State.PICKABLE);

        grid[1][0] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[2][0] = new Card(Card.Color.WHITE, Card.State.PICKABLE);
        grid[1][2] = new Card(Card.Color.WHITE, Card.State.PICKABLE);

        SharedGoal1 goal = new SharedGoal1(2);

        assertEquals(4, goal.getScore(grid));
    }
}
