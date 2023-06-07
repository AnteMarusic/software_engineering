package org.polimi.servernetwork.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BagOfCardsTest {

    private BagOfCards bag;
    private final int arbitraryNumber = 21;
    @BeforeEach
    void setUp() {
        bag = new BagOfCards();
        for(int i=0; i<arbitraryNumber; i++){
            bag.collectCard();}
    }

    @AfterEach
    void tearDown() {
    }
    @Test
    void correctStartingSizeTest(){
        assertEquals(132-arbitraryNumber, bag.getSize());
    }
    @Test
    void collectACardTest(){
        int oldSize = bag.getSize();
        Card card = bag.collectCard();
        assertEquals(oldSize-1, bag.getSize());
        assertNotEquals(null, card);
    }
    @Test
    void collectACardWhenBagIsEmptyTest(){
        int size=bag.getSize();
        for(int i=0; i<size; i++){
            bag.collectCard();}
        assertEquals(0, bag.getSize());
        assertNull(bag.collectCard());
    }

}