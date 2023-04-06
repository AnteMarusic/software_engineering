package org.polimi;

import org.polimi.personal_goal.PersonalGoal;
import org.polimi.shared_goal.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Game {
    private Board board;
    private ArrayList<Player> players = new ArrayList<Player>();
    private int numOfPlayers;
    private BagOfCards bagOfCards;
    private AbstractSharedGoal SharedGoal1;
    private AbstractSharedGoal SharedGoal2;

    public Game(Player[] players) {
        numOfPlayers = players.length;
        this.players.addAll(Arrays.asList(players));
        this.bagOfCards = new BagOfCards();
    }

    public void startGame(){
        Random random = new Random();
        int firstPlayer = random.nextInt(numOfPlayers);
        board = new Board(numOfPlayers, bagOfCards);
        board.fill();
        handOutGoalsPG();
        handOutGoalsSG();
    }

    public void gameLoop(){
    }

    private void handOutGoalsPG(){
        Random random = new Random();
        int personalCode[] = new int[3];
        personalCode[1] = random.nextInt(12);
        do {
            personalCode[2] = random.nextInt(12);
        } while (personalCode[1]==personalCode[2]);
        do {
            personalCode[3] = random.nextInt(12);
        } while (personalCode[2]==personalCode[3] || personalCode[1]==personalCode[3]);


        for (int k=0; k<numOfPlayers;k++){
            int i = random.nextInt(12);
            players.get(k).personalGoal = new PersonalGoal(personalCode[k]); // ho dato al costruttore di personalGoal il numero del personal goal che mi deve dare
        }
        int j=0;
    }
    private void handOutGoalsSG() {
        Random random = new Random();

        int i = random.nextInt(12);
        int j;
        do {
            j = random.nextInt(12);
        } while (j == i);

        AbstractSharedGoal[] implementazioni = new AbstractSharedGoal[]{
                new SharedGoal1(numOfPlayers),
                new SharedGoal2(numOfPlayers),
                new SharedGoal3(numOfPlayers),
                new SharedGoal4(numOfPlayers),
                new SharedGoal5(numOfPlayers),
                new SharedGoal6(numOfPlayers),
                new SharedGoal7(numOfPlayers),
                new SharedGoal8(numOfPlayers),
                new SharedGoal9(numOfPlayers),
                new SharedGoal10(numOfPlayers),
                new SharedGoal11(numOfPlayers),
                new SharedGoal12(numOfPlayers),
        };
        SharedGoal1 = implementazioni[i];
        SharedGoal2 = implementazioni[j];

/*      implementazione brutta che però non li crea tutti può anche essere fatta con lo switch
        if(i==0) SharedGoal1 = new SharedGoal1(numOfPlayers);
        if(i==1) SharedGoal1 = new SharedGoal2(numOfPlayers);
        if(i==2) SharedGoal1 = new SharedGoal3(numOfPlayers);
        if(i==3) SharedGoal1 = new SharedGoal4(numOfPlayers);
        if(i==4) SharedGoal1 = new SharedGoal5(numOfPlayers);
        if(i==5) SharedGoal1 = new SharedGoal6(numOfPlayers);
        if(i==6) SharedGoal1 = new SharedGoal7(numOfPlayers);
        if(i==7) SharedGoal1 = new SharedGoal8(numOfPlayers);
        if(i==8) SharedGoal1 = new SharedGoal9(numOfPlayers);
        // if(i==9) SharedGoal1 = new SharedGoal10(numOfPlayers);
        if(i==10) SharedGoal1 = new SharedGoal11(numOfPlayers);
        if(i==11) SharedGoal1 = new SharedGoal12(numOfPlayers);
        if(i==0) SharedGoal2 = new SharedGoal1(numOfPlayers);
        if(i==1) SharedGoal2 = new SharedGoal2(numOfPlayers);
        if(i==2) SharedGoal2 = new SharedGoal3(numOfPlayers);
        if(i==3) SharedGoal2 = new SharedGoal4(numOfPlayers);
        if(i==4) SharedGoal2 = new SharedGoal5(numOfPlayers);
        if(i==5) SharedGoal2 = new SharedGoal6(numOfPlayers);
        if(i==6) SharedGoal2 = new SharedGoal7(numOfPlayers);
        if(i==7) SharedGoal2 = new SharedGoal8(numOfPlayers);
        if(i==8) SharedGoal2 = new SharedGoal9(numOfPlayers);
        // if(i==9) SharedGoal2 = new SharedGoal10(numOfPlayers);
        if(i==10) SharedGoal2 = new SharedGoal11(numOfPlayers);
        if(i==11) SharedGoal2 = new SharedGoal12(numOfPlayers);

        */

    }



    public Board getBoard() {
        return this.board;
    }
}
