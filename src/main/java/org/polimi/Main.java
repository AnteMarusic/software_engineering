package org.polimi;

import org.polimi.personal_goal.PersonalGoal;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        AbstractCoordinates a = new BoardCoordinates(3, 4);
        System.out.println(a.isValid());
    }
}