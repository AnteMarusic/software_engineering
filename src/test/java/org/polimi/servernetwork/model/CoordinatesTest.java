package org.polimi.servernetwork.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CoordinatesTest {

    private Coordinates coordinates;
    @BeforeEach
    void setUp() {
        coordinates = new Coordinates(4,7);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getRowTest() {
        assertEquals(4, coordinates.getRow());
    }

    @Test
    void getColTest() {
        assertEquals(7, coordinates.getCol());
    }

    @Test
    void testToString() {
        String string= "Coordinates{" +
                "row=" + 4 +
                ", col=" + 7 +
                '}';
        assertEquals(string, coordinates.toString());
    }

    @Test
    void testEquals() {
        Coordinates coordinates1 = new Coordinates(4,7);
        assertEquals(coordinates, coordinates1);
    }

    @Test
    void testNotEquals() {
        Coordinates coordinates1 = new Coordinates(3,7);
        assertNotEquals(coordinates, coordinates1);
    }

    @Test
    void testHashCode() {
    }
}