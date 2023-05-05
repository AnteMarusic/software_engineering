package org.polimi.server.model;

import org.polimi.server.model.goal.Goal;
import org.polimi.server.model.goal.PersonalGoal;
import org.polimi.server.model.goal.shared_goal.*;

import java.util.*;

public class Game{
    private boolean endGame = false;
    private final Board board;
    private final Player[] players;
    private final Goal[] sharedGoal;
    //private final Goal boardGoal;
    private final int firstPlayer;
    private int ender;

    public Game(int numOfPlayer, int firstPlayer, String[] playerName) {
        this.board = new Board(numOfPlayer);
        this.players = new Player[numOfPlayer];
        for (int j=0; j<numOfPlayer; j++){
            players[j] = new Player(playerName[j]);
        }
        handOutGoalsPG(numOfPlayer);
        sharedGoal = new Goal[2]; // non sono sicuro si faccia così
        handOutGoalsSG(numOfPlayer);
        this.firstPlayer = firstPlayer;
    }



    private void handOutGoalsPG(int numOfPlayers){
        Random random = new Random();
        int[] personalCode = new int[numOfPlayers];
        randomAssignment(random, personalCode, numOfPlayers, players);
    }
    private static void randomAssignment(Random random, int[] personalCode, int numOfPlayers, Player[] players) {
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
            players[k].setPersonalGoal(new PersonalGoal(personalCode[k]));
        }
    }

    private void handOutGoalsSG(int numOfPlayers) {
        Random random = new Random();

        int i = random.nextInt(12);
        int j;
        do {
            j = random.nextInt(12);
        } while (j == i);

        if (i == 0) sharedGoal[1] = new SharedGoal1(numOfPlayers);  // non sono sicuro si faccia così
        if (i == 1) sharedGoal[1] = new SharedGoal2(numOfPlayers);
        if (i == 2) sharedGoal[1] = new SharedGoal3(numOfPlayers);
        if (i == 3) sharedGoal[1] = new SharedGoal4(numOfPlayers);
        if (i == 4) sharedGoal[1] = new SharedGoal5(numOfPlayers);
        if (i == 5) sharedGoal[1] = new SharedGoal6(numOfPlayers);
        if (i == 6) sharedGoal[1] = new SharedGoal7(numOfPlayers);
        if (i == 7) sharedGoal[1] = new SharedGoal8(numOfPlayers);
        if (i == 8) sharedGoal[1] = new SharedGoal9(numOfPlayers);
        if (i == 9) sharedGoal[1] = new SharedGoal10(numOfPlayers);
        if (i == 10) sharedGoal[1] = new SharedGoal11(numOfPlayers);
        if (i == 11) sharedGoal[1] = new SharedGoal12(numOfPlayers);
        if (j == 0) sharedGoal[2] = new SharedGoal1(numOfPlayers);
        if (j == 1) sharedGoal[2] = new SharedGoal2(numOfPlayers);
        if (j == 2) sharedGoal[2] = new SharedGoal3(numOfPlayers);
        if (j == 3) sharedGoal[2] = new SharedGoal4(numOfPlayers);
        if (j == 4) sharedGoal[2] = new SharedGoal5(numOfPlayers);
        if (j == 5) sharedGoal[2] = new SharedGoal6(numOfPlayers);
        if (j == 6) sharedGoal[2] = new SharedGoal7(numOfPlayers);
        if (j == 7) sharedGoal[2] = new SharedGoal8(numOfPlayers);
        if (j == 8) sharedGoal[2] = new SharedGoal9(numOfPlayers);
        if (j == 9) sharedGoal[2] = new SharedGoal10(numOfPlayers);
        if (j == 10) sharedGoal[2] = new SharedGoal11(numOfPlayers);
        if (j == 11) sharedGoal[2] = new SharedGoal12(numOfPlayers);
    }

    public void remove(List<Coordinates> coordinates){
        //board.removeCardAtCoordinate(coordinates); // biosgna riscrivere il metodo remove in Board siccome ora deve solamente rimuovere senza fare alcun controllo
    }
    public int insertInBookshelf( int column, int currentPlayer){
        // devo capire dove sono le carte che voglio inserire
        // if(bookshelf is full && endGame==false){
        //      endGame=true;
        //      setto il giocatore corrente come ender
        // aggiorna il punteggio del player controllando i vari obbiettivi
        // players[i].setTotalScore(players[i].getSharedScore() + players[i].getPersonalScore());
        // e lo ritorna
        return players[currentPlayer].getTotalScore();
    }
    public HashMap<String,Integer> endGame(){
        players[ender].updateTotalscore(1);

        // ordino l'array di player in base ha quanti punti hanno fatto, tanto non mi interessano più le posizioni
        Arrays.sort(players, new Comparator<Player>(){       // non so cosa accada se due giocatori hanno gli stessi punti
            public int compare (Player player1, Player player2) {
                return player2.getTotalScore() - player1.getTotalScore();
            }

        });
        HashMap<String,Integer> ranking = new HashMap<String,Integer>();
        for(int i=0; i<players.length; i++){
            ranking.put(players[i].getName(), players[i].getTotalScore());
        }
        return ranking;
    }


    public Board getBoard() {
        return board;
    }

    public Player[] getPlayers() {
        return players;
    }

    /*public Goal[] getPersonalGoal() {
        return personalGoal;
    }
     */

    public Goal[] getSharedGoal() {
        return sharedGoal;
    }
    public boolean getEndGame(){
        return endGame;
    }
}
