package org.polimi.servernetwork.model.goal.GoalTest;

import org.junit.jupiter.api.Test;
import org.polimi.client.ClientBookshelf;
import org.polimi.servernetwork.model.Bookshelf;
import org.polimi.servernetwork.model.Card;
import org.polimi.servernetwork.model.goal.Goal;
import org.polimi.servernetwork.model.goal.shared_goal.SharedGoal12;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SharedGoal12Test {
    @Test
    void expectedTrue1(){
        Card[][] grid = new Card[6][5];
        grid[4][4] = new Card(Card.Color.ORANGE, Card.State.PICKABLE, 0);
        grid[3][4] = new Card(Card.Color.ORANGE, Card.State.PICKABLE, 0);
        grid[2][4] = new Card(Card.Color.ORANGE, Card.State.PICKABLE, 0);
        grid[1][4] = new Card(Card.Color.ORANGE, Card.State.PICKABLE, 0);
        grid[0][4] = new Card(Card.Color.ORANGE, Card.State.PICKABLE, 0);

        grid[5][3] = new Card(Card.Color.ORANGE, Card.State.PICKABLE, 0);
        grid[4][3] = new Card(Card.Color.ORANGE, Card.State.PICKABLE, 0);
        grid[5][4] = new Card(Card.Color.ORANGE, Card.State.PICKABLE, 0);
        grid[3][3] = new Card(Card.Color.ORANGE, Card.State.PICKABLE, 0);
        grid[2][3] = new Card(Card.Color.ORANGE, Card.State.PICKABLE, 0);
        grid[1][3] = new Card(Card.Color.ORANGE, Card.State.PICKABLE, 0);

        grid[5][2] = new Card(Card.Color.ORANGE, Card.State.PICKABLE, 0);
        grid[4][2] = new Card(Card.Color.ORANGE, Card.State.PICKABLE, 0);
        grid[3][2] = new Card(Card.Color.ORANGE, Card.State.PICKABLE, 0);
        grid[2][2] = new Card(Card.Color.ORANGE, Card.State.PICKABLE, 0);

        grid[5][1] = new Card(Card.Color.ORANGE, Card.State.PICKABLE, 0);
        grid[4][1] = new Card(Card.Color.ORANGE, Card.State.PICKABLE, 0);
        grid[3][1] = new Card(Card.Color.ORANGE, Card.State.PICKABLE, 0);

        grid[5][0] = new Card(Card.Color.ORANGE, Card.State.PICKABLE, 0);
        grid[4][0] = new Card(Card.Color.ORANGE, Card.State.PICKABLE, 0);
        Goal goal = new SharedGoal12(2);
        assertEquals(4, goal.getScore(grid));
        ClientBookshelf bookshelf = new ClientBookshelf();
        bookshelf.setGrid(grid);
        bookshelf.printMyBookshelf();
        System.out.println("1");
    }

    @Test
    void expectedTrue2(){
        Card[][] grid = new Card[6][5];
        grid[5][4] = new Card(Card.Color.ORANGE, Card.State.PICKABLE, 0);
        grid[4][4] = new Card(Card.Color.ORANGE, Card.State.PICKABLE, 0);
        grid[3][4] = new Card(Card.Color.ORANGE, Card.State.PICKABLE, 0);
        grid[2][4] = new Card(Card.Color.ORANGE, Card.State.PICKABLE, 0);
        grid[1][4] = new Card(Card.Color.ORANGE, Card.State.PICKABLE, 0);

        grid[5][3] = new Card(Card.Color.ORANGE, Card.State.PICKABLE, 0);
        grid[4][3] = new Card(Card.Color.ORANGE, Card.State.PICKABLE, 0);
        grid[3][3] = new Card(Card.Color.ORANGE, Card.State.PICKABLE, 0);
        grid[2][3] = new Card(Card.Color.ORANGE, Card.State.PICKABLE, 0);

        grid[5][2] = new Card(Card.Color.ORANGE, Card.State.PICKABLE, 0);
        grid[4][2] = new Card(Card.Color.ORANGE, Card.State.PICKABLE, 0);
        grid[3][2] = new Card(Card.Color.ORANGE, Card.State.PICKABLE, 0);

        grid[5][1] = new Card(Card.Color.ORANGE, Card.State.PICKABLE, 0);
        grid[4][1] = new Card(Card.Color.ORANGE, Card.State.PICKABLE, 0);

        grid[5][0] = new Card(Card.Color.ORANGE, Card.State.PICKABLE, 0);

        Goal goal = new SharedGoal12(2);
        assertEquals(4, goal.getScore(grid));
        ClientBookshelf bookshelf = new ClientBookshelf();
        bookshelf.setGrid(grid);
        bookshelf.printMyBookshelf();
    }

    @Test
    void expectedTrue3(){
        Card[][] grid = new Card[6][5];
        grid[5][0] = new Card(Card.Color.ORANGE, Card.State.PICKABLE, 0);
        grid[4][0] = new Card(Card.Color.ORANGE, Card.State.PICKABLE, 0);
        grid[3][0] = new Card(Card.Color.ORANGE, Card.State.PICKABLE, 0);
        grid[2][0] = new Card(Card.Color.ORANGE, Card.State.PICKABLE, 0);
        grid[1][0] = new Card(Card.Color.ORANGE, Card.State.PICKABLE, 0);

        grid[5][1] = new Card(Card.Color.ORANGE, Card.State.PICKABLE, 0);
        grid[4][1] = new Card(Card.Color.ORANGE, Card.State.PICKABLE, 0);
        grid[3][1] = new Card(Card.Color.ORANGE, Card.State.PICKABLE, 0);
        grid[2][1] = new Card(Card.Color.ORANGE, Card.State.PICKABLE, 0);

        grid[5][2] = new Card(Card.Color.ORANGE, Card.State.PICKABLE, 0);
        grid[4][2] = new Card(Card.Color.ORANGE, Card.State.PICKABLE, 0);
        grid[3][2] = new Card(Card.Color.ORANGE, Card.State.PICKABLE, 0);

        grid[5][3] = new Card(Card.Color.ORANGE, Card.State.PICKABLE,0);
        grid[4][3] = new Card(Card.Color.ORANGE, Card.State.PICKABLE,0);

        grid[5][4] = new Card(Card.Color.ORANGE, Card.State.PICKABLE,0);

        Goal goal = new SharedGoal12(2);
        assertEquals(4, goal.getScore(grid));
        ClientBookshelf bookshelf = new ClientBookshelf();
        bookshelf.setGrid(grid);
        bookshelf.printMyBookshelf();
    }

    @Test
    void expectedTrue4(){
        Card[][] grid = new Card[6][5];
        grid[5][0] = new Card(Card.Color.ORANGE, Card.State.PICKABLE,0);
        grid[4][0] = new Card(Card.Color.ORANGE, Card.State.PICKABLE,0);
        grid[3][0] = new Card(Card.Color.ORANGE, Card.State.PICKABLE,0);
        grid[2][0] = new Card(Card.Color.ORANGE, Card.State.PICKABLE,0);
        grid[1][0] = new Card(Card.Color.ORANGE, Card.State.PICKABLE,0);
        grid[0][0] = new Card(Card.Color.ORANGE, Card.State.PICKABLE,0);

        grid[5][1] = new Card(Card.Color.ORANGE, Card.State.PICKABLE,0);
        grid[4][1] = new Card(Card.Color.ORANGE, Card.State.PICKABLE,0);
        grid[3][1] = new Card(Card.Color.ORANGE, Card.State.PICKABLE,0);
        grid[2][1] = new Card(Card.Color.ORANGE, Card.State.PICKABLE,0);
        grid[1][1] = new Card(Card.Color.ORANGE, Card.State.PICKABLE,0);

        grid[5][2] = new Card(Card.Color.ORANGE, Card.State.PICKABLE,0);
        grid[4][2] = new Card(Card.Color.ORANGE, Card.State.PICKABLE,0);
        grid[3][2] = new Card(Card.Color.ORANGE, Card.State.PICKABLE,0);
        grid[2][2] = new Card(Card.Color.ORANGE, Card.State.PICKABLE,0);

        grid[5][3] = new Card(Card.Color.ORANGE, Card.State.PICKABLE,0);
        grid[4][3] = new Card(Card.Color.ORANGE, Card.State.PICKABLE,0);
        grid[3][3] = new Card(Card.Color.ORANGE, Card.State.PICKABLE,0);

        grid[5][4] = new Card(Card.Color.ORANGE, Card.State.PICKABLE,0);
        grid[4][4] = new Card(Card.Color.ORANGE, Card.State.PICKABLE,0);

        Goal goal = new SharedGoal12(2);
        assertEquals(4, goal.getScore(grid));
        ClientBookshelf bookshelf = new ClientBookshelf();
        bookshelf.setGrid(grid);
        bookshelf.printMyBookshelf();
    }

    @Test
    void expectedFalse(){
        Card[][] grid = new Card[6][5];
        grid[5][0] = new Card(Card.Color.ORANGE, Card.State.PICKABLE,0);
        grid[4][0] = new Card(Card.Color.ORANGE, Card.State.PICKABLE,0);
        grid[3][0] = new Card(Card.Color.ORANGE, Card.State.PICKABLE,0);
        grid[2][0] = new Card(Card.Color.ORANGE, Card.State.PICKABLE,0);
        grid[1][0] = new Card(Card.Color.ORANGE, Card.State.PICKABLE,0);
        grid[0][0] = new Card(Card.Color.ORANGE, Card.State.PICKABLE,0);

        grid[5][1] = new Card(Card.Color.ORANGE, Card.State.PICKABLE,0);
        grid[4][1] = new Card(Card.Color.ORANGE, Card.State.PICKABLE,0);
        grid[3][1] = new Card(Card.Color.ORANGE, Card.State.PICKABLE,0);
        grid[2][1] = new Card(Card.Color.ORANGE, Card.State.PICKABLE,0);
        grid[1][1] = new Card(Card.Color.ORANGE, Card.State.PICKABLE,0);

        grid[5][2] = new Card(Card.Color.ORANGE, Card.State.PICKABLE,0);
        grid[4][2] = new Card(Card.Color.ORANGE, Card.State.PICKABLE,0);
        grid[3][2] = new Card(Card.Color.ORANGE, Card.State.PICKABLE,0);
        grid[2][2] = new Card(Card.Color.ORANGE, Card.State.PICKABLE,0);

        grid[5][3] = new Card(Card.Color.ORANGE, Card.State.PICKABLE,0);
        grid[4][3] = new Card(Card.Color.ORANGE, Card.State.PICKABLE,0);
        grid[3][3] = new Card(Card.Color.ORANGE, Card.State.PICKABLE,0);

        grid[5][4] = new Card(Card.Color.ORANGE, Card.State.PICKABLE,0);
        grid[4][4] = new Card(Card.Color.ORANGE, Card.State.PICKABLE,0);
        grid[3][4] = new Card(Card.Color.ORANGE, Card.State.PICKABLE,0);

        Goal goal = new SharedGoal12(2);
        assertEquals(0, goal.getScore(grid));
        ClientBookshelf bookshelf = new ClientBookshelf();
        bookshelf.setGrid(grid);
        bookshelf.printMyBookshelf();
    }
}
