package org.polimi.server.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.polimi.server.model.Card.State.PICKABLE;

class BoardTest {
    private Board board;
    @BeforeEach
    void setUp() {
        board = new Board(3);
    }

    @AfterEach
    void tearDown() {
    }
    @Test
    void seeCardAtCoordinates() {
        Card card;
        //out of bound card
        card = board.seeCardAtCoordinates(new Coordinates(2,1));
        assertNull(card);
        //card in bound only if numOfPlayers >= 3
        card = board.seeCardAtCoordinates(new Coordinates(0,3));
        assertNotNull(card);
        //card always in bound
        card = board.seeCardAtCoordinates(new Coordinates(3,4));
        assertNotNull(card);
        //card in bound only if numOfPlayers = 4
        card = board.seeCardAtCoordinates(new Coordinates(0,4));
        assertNull(card);
    }
    @Test
    void getCardAtCoordinatesTest() {
        Card card, pickedCard, adjacentCard;
        System.out.println("before test");
        board.printMap();
        //see a card always in bound
        card = board.seeCardAtCoordinates(new Coordinates(3,4));
        System.out.println("test");
        board.printMap();
        //get a card that should not be pickable
        pickedCard = board.getCardAtCoordinates(new Coordinates(3,4));
        System.out.println("test");
        board.printMap();
        assertNull(pickedCard);
        assertEquals(card, board.seeCardAtCoordinates(new Coordinates(3, 4)));
        System.out.println("test");
        board.printMap();
        //see a card in bound only if numOfPlayers >= 3
        card = board.seeCardAtCoordinates(new Coordinates(2,2));
        System.out.println("test");
        board.printMap();
        //see adjacent card (should be not pickable if numOfPlayers == 3)
        adjacentCard=board.seeCardAtCoordinates(new Coordinates(2,3));
        assertNotEquals(PICKABLE, adjacentCard.getState());
        //get a card that should be pickable
        pickedCard=board.getCardAtCoordinates(new Coordinates(2,2));
        adjacentCard=board.seeCardAtCoordinates(new Coordinates(2,3));
        assertEquals(card, pickedCard);
        assertEquals(PICKABLE, adjacentCard.getState());
        assertNull(board.seeCardAtCoordinates(new Coordinates(2,2)));

    }

    @Test
    void refillCheck() {
    }
}