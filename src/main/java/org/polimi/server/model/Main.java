package org.polimi.server.model;

public class Main {
    public static void main(String[] args) {
        AbstractCoordinates a = new BoardCoordinates(3, 4);
        System.out.println(a.isValid());
    }
}