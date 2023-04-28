package org.polimi.server.controller;

import org.polimi.server.model.BagOfCards;
import org.polimi.server.model.Board;
import org.polimi.server.model.Player;
import org.polimi.server.model.goal.PersonalGoal;
import org.polimi.server.model.goal.shared_goal.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class GameController {
    private int ender;
    private Board board;
    private int firstPlayer;
    boolean endGame = false;
    private final ArrayList<Player> players = new ArrayList<Player>();
    private final int numOfPlayers;
    private final BagOfCards bagOfCards;
    private AbstractSharedGoal SharedGoal1;
    private AbstractSharedGoal SharedGoal2;

    public GameController(Player[] players) {
        numOfPlayers = players.length;
        this.players.addAll(Arrays.asList(players));
        this.bagOfCards = new BagOfCards();
    }

    public void startGame(){
        Random random = new Random();
        firstPlayer = random.nextInt(numOfPlayers);
        board = new Board(numOfPlayers, bagOfCards);
        board.fill();
        handOutGoalsPG();
        handOutGoalsSG();
    }

    public void gameLoop(){
        int currentPlayer = firstPlayer;
        do{

            players.get(currentPlayer).chooseCards(this.board);
            //cotrollo se ha raggiunto il primo sharedGoal
            if(!players.get(currentPlayer).getSharedGoal1Achieved())
            {
                int newPoint;
                newPoint = SharedGoal1.getScore(players.get(currentPlayer).getGrid());
                players.get(currentPlayer).increaseSharedScore(newPoint);
                if (newPoint != 0)
                    players.get(currentPlayer).setSharedGoal1AchievedToTrue();
            }

            // controllo se ha raggiunto il secondo sharedGoal
            if(!players.get(currentPlayer).getSharedGoal2Achieved())
            {
                int newPoint;
                newPoint = SharedGoal2.getScore(players.get(currentPlayer).getGrid());
                players.get(currentPlayer).increaseSharedScore(newPoint);
                if (newPoint != 0)
                    players.get(currentPlayer).setSharedGoal2AchievedToTrue();
            }
            endGame = players.get(currentPlayer).getIsBookshelfFull();

            if(endGame){
                ender = currentPlayer;
            }

            if(board.refillCheck())
                board.fill();
            currentPlayer = (currentPlayer+1) % numOfPlayers;
        }while(!endGame || currentPlayer!=firstPlayer);

        endGame(ender);


    }

    public void winGame(int winner){
        brodcastWinMessage(players.get(winner).getName());
    }

    public void endGame(int ender){
        int winnerpoints;
        int winner = 0;
        int[] totalPoints = new int [numOfPlayers];
        for (int i = 0; i<numOfPlayers; i++){
            totalPoints[i] = players.get(i).getSharedScore() + players.get(i).getPersonalScore();
            if(i == ender)
                totalPoints[i]++;
        }
        winnerpoints = Arrays.stream(totalPoints).max().getAsInt();
        for (int i = 0; i<numOfPlayers; i++){
            if(totalPoints[i]== winnerpoints){
                winner = i;
            }
        }
        winGame(winner);



    }




    private void handOutGoalsPG(){
        Random random = new Random();
        int[] personalCode = new int[numOfPlayers];
        randomAssignment(random, personalCode, numOfPlayers, players);
    }

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
        if(i==9) SharedGoal1 = new SharedGoal10(numOfPlayers);
        if(i==10) SharedGoal1 = new SharedGoal11(numOfPlayers);
        if(i==11) SharedGoal1 = new SharedGoal12(numOfPlayers);
        if(j==0) SharedGoal2 = new SharedGoal1(numOfPlayers);
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
    public int getFirstPlayer(){
        return this.firstPlayer;
    }


}
