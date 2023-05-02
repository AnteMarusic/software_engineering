package org.polimi.server.model;

import org.polimi.server.model.goal.Goal;
import org.polimi.server.model.goal.PersonalGoal;
import org.polimi.server.model.goal.shared_goal.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Game{
    private final Board board;
    private final Player[] players;
    private final Goal[] personalGoal;
    private final Goal[] sharedGoal;
    //private final Goal boardGoal;

    public Game(int numOfPlayer) {
        this.board = new Board(numOfPlayer);
        this.players = new Player[numOfPlayer];
        this.personalGoal = new Goal[numOfPlayer];
        this.sharedGoal = new Goal[2];
        //this.boardGoal =;
    }

    public void addPlayer (String username) {}

    private static void randomAssignment(Random random, int[] personalCode, int numOfPlayers, ArrayList<Player> players) {
        personalCode[1] = random.nextInt(12);
        do {
            personalCode[2] = random.nextInt(12);
        } while (personalCode[1]==personalCode[2]);
        if(numOfPlayers > 2) {
            do {
                personalCode[3] = random.nextInt(12);
            } while (personalCode[2] == personalCode[3] || personalCode[1] == personalCode[3]);
        }
        if(numOfPlayers > 3) {
            do {
                personalCode[4] = random.nextInt(12);
            } while (personalCode[3]==personalCode[4] || personalCode[2] == personalCode[4] || personalCode[1] == personalCode[4]);
        }


        for (int k = 0; k< numOfPlayers; k++){
            players.get(k).setPersonalGoal(new PersonalGoal(personalCode[k]));
        }
    }

    private void handOutGoalsSG() {
        Random random = new Random();

        int i = random.nextInt(12);
        int j;
        do {
            j = random.nextInt(12);
        } while (j == i);

        AbstractSharedGoal sharedGoal1;
        AbstractSharedGoal sharedGoal2;
        int numOfPlayers = players.length;

        if (i == 0) sharedGoal1 = new SharedGoal1(numOfPlayers);
        if (i == 1) sharedGoal1 = new SharedGoal2(numOfPlayers);
        if (i == 2) sharedGoal1 = new SharedGoal3(numOfPlayers);
        if (i == 3) sharedGoal1 = new SharedGoal4(numOfPlayers);
        if (i == 4) sharedGoal1 = new SharedGoal5(numOfPlayers);
        if (i == 5) sharedGoal1 = new SharedGoal6(numOfPlayers);
        if (i == 6) sharedGoal1 = new SharedGoal7(numOfPlayers);
        if (i == 7) sharedGoal1 = new SharedGoal8(numOfPlayers);
        if (i == 8) sharedGoal1 = new SharedGoal9(numOfPlayers);
        if (i == 9) sharedGoal1 = new SharedGoal10(numOfPlayers);
        if (i == 10) sharedGoal1 = new SharedGoal11(numOfPlayers);
        if (i == 11) sharedGoal1 = new SharedGoal12(numOfPlayers);
        if (j == 0) sharedGoal2 = new SharedGoal1(numOfPlayers);
        if (i == 1) sharedGoal2 = new SharedGoal2(numOfPlayers);
        if (j == 2) sharedGoal2 = new SharedGoal3(numOfPlayers);
        if (i == 3) sharedGoal2 = new SharedGoal4(numOfPlayers);
        if (i == 4) sharedGoal2 = new SharedGoal5(numOfPlayers);
        if (i == 5) sharedGoal2 = new SharedGoal6(numOfPlayers);
        if (i == 6) sharedGoal2 = new SharedGoal7(numOfPlayers);
        if (i == 7) sharedGoal2 = new SharedGoal8(numOfPlayers);
        if (i == 8) sharedGoal2 = new SharedGoal9(numOfPlayers);
        if (i == 9) sharedGoal2 = new SharedGoal10(numOfPlayers);
        if (i == 10) sharedGoal2 = new SharedGoal11(numOfPlayers);
        if (j == 11) sharedGoal2 = new SharedGoal12(numOfPlayers);
    }

    public Board getBoard() {
        return board;
    }

    public Player[] getPlayers() {
        return players;
    }

    public Goal[] getPersonalGoal() {
        return personalGoal;
    }

    public Goal[] getSharedGoal() {
        return sharedGoal;
    }
}
