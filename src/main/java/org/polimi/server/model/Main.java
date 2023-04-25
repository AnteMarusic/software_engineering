package org.polimi.server.model;

import org.polimi.server.model.coordinates.AbstractCoordinates;
import org.polimi.server.model.coordinates.BoardCoordinates;

public class Main {
    public static void main(String[] args) {
        AbstractCoordinates a = new BoardCoordinates(3, 4);
        System.out.println(a.isValid());
    }
}