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
        card = board.seeCardAtCoordinates(new Coordinates(2,1));
        assertNull(card);
        card = board.seeCardAtCoordinates(new Coordinates(0,3));
        assertNotNull(card);
        card = board.seeCardAtCoordinates(new Coordinates(3,4));
        assertNotNull(card);
        card = board.seeCardAtCoordinates(new Coordinates(0,4));
        assertNull(card);
    }
    @Test
    void getCardAtCoordinatesTest() {
        Card card, pickedCard, adjacentCard;

        card = board.seeCardAtCoordinates(new Coordinates(3,4));
        pickedCard = board.getCardAtCoordinates(new Coordinates(3,4));
        assertNull(pickedCard);
        assertEquals(card, board.seeCardAtCoordinates(new Coordinates(3, 4)));

        card = board.seeCardAtCoordinates(new Coordinates(2,2));
        adjacentCard=board.seeCardAtCoordinates(new Coordinates(2,3));
        assertNotEquals(PICKABLE, adjacentCard.getState());
        pickedCard=board.getCardAtCoordinates(new Coordinates(2,2));
        adjacentCard=board.seeCardAtCoordinates(new Coordinates(2,3));
        assertEquals(card, pickedCard);
        assertEquals(PICKABLE, adjacentCard.getState());
        assertNull(board.seeCardAtCoordinates(new Coordinates(2,2)));

    }

    @Test
    void refillCheck() {
    }

    @Test
    void printBoard() {
    }

    @Test
    void printMap() {
    }
}