package org.polimi.server.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.concurrent.CancellationException;

import static org.junit.jupiter.api.Assertions.*;

public class BookshelfTest {
    private Bookshelf b;
    @BeforeEach
    void setUp() {
        b = new Bookshelf();
    }

    @Test
    void insertTest(){
        LinkedList<Card> l= new LinkedList<Card>();
        Card[][] result;
        l.add(new Card(Card.Color.BLUE, Card.State.PICKABLE));
        b.insert(l, 1);
        result = b.getGrid();
        assertEquals(new Card(Card.Color.BLUE, Card.State.PICKABLE), result[5][1]);
        assertEquals(5, b.getInsertable(1));
        assertEquals(3, b.getMaxInsertable());

        l.clear();

        l.add(new Card(Card.Color.BLUE, Card.State.PICKABLE));
        l.add(new Card(Card.Color.ORANGE, Card.State.PICKABLE));
        l.add(new Card(Card.Color.PINK, Card.State.PICKABLE));
        b.insert(l, 1);
        assertEquals(new Card(Card.Color.BLUE, Card.State.PICKABLE), result[5][1]);
        assertEquals(new Card(Card.Color.BLUE, Card.State.PICKABLE), result[4][1]);
        assertEquals(new Card(Card.Color.ORANGE, Card.State.PICKABLE), result[3][1]);
        assertEquals(new Card(Card.Color.PINK, Card.State.PICKABLE), result[2][1]);
        assertEquals(2, b.getInsertable(1));
        assertEquals(6, b.getInsertable(0));
        assertEquals(3, b.getMaxInsertable());

        b.insert(l, 3);
        assertEquals(new Card(Card.Color.BLUE, Card.State.PICKABLE), result[5][3]);
        assertEquals(new Card(Card.Color.ORANGE, Card.State.PICKABLE), result[4][3]);
        assertEquals(new Card(Card.Color.PINK, Card.State.PICKABLE), result[3][3]);
        assertEquals(3, b.getInsertable(3));
        assertEquals(3, b.getMaxInsertable());
        assertFalse(b.checkIfFull());

        b.insert(l, 0);
        b.insert(l, 0);
        assertEquals(0, b.getInsertable(0));

        b.insert(l, 2);
        b.insert(l, 2);
        assertEquals(0, b.getInsertable(2));

        b.insert(l, 4);
        b.insert(l, 4);
        assertEquals(0, b.getInsertable(4));

        assertEquals(3, b.getMaxInsertable());

        l.clear();

        l.add(new Card (Card.Color.WHITE, Card.State.PICKABLE));
        b.insert(l, 3);

        assertEquals(2, b.getMaxInsertable());
    }
}
