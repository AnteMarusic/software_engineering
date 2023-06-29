package org.polimi.servernetwork.model.goal.GoalTest;

import org.junit.jupiter.api.Test;
import org.polimi.client.ClientBoard;
import org.polimi.client.ClientBookshelf;
import org.polimi.servernetwork.model.Card;
import org.polimi.servernetwork.model.goal.Goal;
import org.polimi.servernetwork.model.goal.shared_goal.SharedGoal9;

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
        assertEquals(8, goal.getScore(grid));
        ClientBookshelf bookshelf = new ClientBookshelf(grid);
        bookshelf.printMyBookshelf();
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
        ClientBookshelf bookshelf = new ClientBookshelf(grid);
        bookshelf.printMyBookshelf();
    }
}
