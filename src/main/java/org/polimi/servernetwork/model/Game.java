package org.polimi.servernetwork.model;

import org.polimi.servernetwork.model.goal.BoardGoal;
import org.polimi.servernetwork.model.goal.Goal;
import org.polimi.servernetwork.model.goal.PersonalGoal;
import org.polimi.servernetwork.model.goal.shared_goal.*;

import java.io.Serializable;
import java.util.*;

public class Game implements Serializable {
    private boolean endGame = false;
    private final Player[] players;
    private final Board board;
    private final Goal[] sharedGoal;
    private final Goal boardGoal;
    private int[] chosenSharedGoal;
    private final int firstPlayer;
    private int ender;
    private List<Card> readyToInsert;

    public Game(int numOfPlayer, int firstPlayer, List<String> playerName) {
        this.board = new Board (numOfPlayer);
        this.players = new Player[numOfPlayer];
        this.readyToInsert = new LinkedList<>();
        for (int j=0; j<numOfPlayer; j++){
            players[j] = new Player(playerName.get(j));
            System.out.println("(Game) " + j + "° giocatore è " + players[j].getName());
        }
        handOutGoalsPG(numOfPlayer);
        sharedGoal = new Goal[2];
        handOutGoalsSG(numOfPlayer);
        this.firstPlayer = firstPlayer;
        this.boardGoal = new BoardGoal();
    }

    public int getFirstPlayer() {
        return firstPlayer;
    }
    public int getNumOfPlayers () {
        return players.length;
    }

    /**
     * for testing purposes
     * @return the array of coordinates
     */
    public Coordinates[] getPersonalGoalCoordinates(int i) {
        return players[i].getPersonalGoalCoordinates();
    }

    /**
     * for testing purposes
     * @return the array of colors
     */
    public Card.Color[] getPersonalGoalColors(int i) {
        return players[i].getPersonalGoalColors();
    }



    private void handOutGoalsPG(int numOfPlayers){
        Random random = new Random();
        int[] personalCode = new int[numOfPlayers];
        randomAssignment(random, personalCode, numOfPlayers, players);
    }
    private static void randomAssignment(Random random, int[] personalCode, int numOfPlayers, Player[] players) {
        personalCode[0] = random.nextInt(12);
        do {
            personalCode[1] = random.nextInt(12);
        } while (personalCode[0]==personalCode[1]);
        if(numOfPlayers > 2) {
            do {
                personalCode[2] = random.nextInt(12);
            } while (personalCode[1] == personalCode[2] || personalCode[0] == personalCode[2]);
        }
        if(numOfPlayers > 3) {
            do {
                personalCode[3] = random.nextInt(12);
            } while (personalCode[2]==personalCode[3] || personalCode[1] == personalCode[3] || personalCode[0] == personalCode[3]);
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
        chosenSharedGoal = new int[2];
        chosenSharedGoal[0]=i;
        chosenSharedGoal[1]=j;

        if (i == 2) sharedGoal[0] = new SharedGoal3(numOfPlayers);
        if (i == 3) sharedGoal[0] = new SharedGoal4(numOfPlayers);
        if (i == 4) sharedGoal[0] = new SharedGoal5(numOfPlayers);
        if (i == 1) sharedGoal[0] = new SharedGoal2(numOfPlayers);
        if (i == 5) sharedGoal[0] = new SharedGoal6(numOfPlayers);
        if (i == 6) sharedGoal[0] = new SharedGoal7(numOfPlayers);
        if (i == 7) sharedGoal[0] = new SharedGoal8(numOfPlayers);
        if (i == 8) sharedGoal[0] = new SharedGoal9(numOfPlayers);
        if (i == 0) sharedGoal[0] = new SharedGoal1(numOfPlayers);
        if (i == 9) sharedGoal[0] = new SharedGoal10(numOfPlayers);
        if (i == 10) sharedGoal[0] = new SharedGoal11(numOfPlayers);
        if (i == 11) sharedGoal[0] = new SharedGoal12(numOfPlayers);
        if (j == 0) sharedGoal[1] = new SharedGoal1(numOfPlayers);
        if (j == 1) sharedGoal[1] = new SharedGoal2(numOfPlayers);
        if (j == 2) sharedGoal[1] = new SharedGoal3(numOfPlayers);
        if (j == 3) sharedGoal[1] = new SharedGoal4(numOfPlayers);
        if (j == 4) sharedGoal[1] = new SharedGoal5(numOfPlayers);
        if (j == 5) sharedGoal[1] = new SharedGoal6(numOfPlayers);
        if (j == 6) sharedGoal[1] = new SharedGoal7(numOfPlayers);
        if (j == 7) sharedGoal[1] = new SharedGoal8(numOfPlayers);
        if (j == 8) sharedGoal[1] = new SharedGoal9(numOfPlayers);
        if (j == 9) sharedGoal[1] = new SharedGoal10(numOfPlayers);
        if (j == 10) sharedGoal[1] = new SharedGoal11(numOfPlayers);
        if (j == 11) sharedGoal[1] = new SharedGoal12(numOfPlayers);
    }

    public boolean remove(List<Coordinates> coordinates){
        // salvo le carte e le rimuovo, metto la prima che mi manda in posizione 0
        readyToInsert.clear();
        for(int i=0; i<coordinates.size(); i++) {
            readyToInsert.add(board.getCardAtCoordinates(coordinates.get(i)));   // va corretta getCardAtCoordinates siccome fa controlli inutili
        }
        return board.refillCheck();
    }

    public List<Coordinates> geToUpdateToPickable () {
        return board.getToUpdateToPickable();
    }

    /**
     *
     * @param column
     * @param currentPlayer
     * @return
     */
    public int insertInBookshelf( int column, int currentPlayer){
        players[currentPlayer].insertInBookshelf(readyToInsert, column);
        if(players[currentPlayer].checkIfBookshelfIsFull() && !endGame){
            endGame = true;
            ender = currentPlayer;
        }
        // aggiorna il punteggio del player controllando i vari obbiettivi
        // players[i].setTotalScore(players[i].getSharedScore() + players[i].getPersonalScore());
        // e lo ritorna

        updateScore(currentPlayer);
        return players[currentPlayer].getTotalScore();
    }

    public Map<String,Integer> endGame(){

        if(endGame){
            players[ender].setWinPoint();
        }

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
    public Map<Coordinates, Card> getBoardMap(){
        return board.getGrid();
    }
    public Card[][] getBookshelfGrid(int position) {
        return players[position].getGrid();
    }
    public int getIndexSharedGoal1(){
        return chosenSharedGoal[0];
    }
    public int getIndexSharedGoal2(){
        return chosenSharedGoal[1];
    }
    public int getPersonalGoalIndex(int position){
        return players[position].getPersonalGoalIndex();
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

    /**
     * updates personal and shared score
     * @param currentPlayer is the player that inserted in bookshelf
     */
    private void updateScore(int currentPlayer){
        System.out.println("(Game updateScore) old personal score for player " + players[currentPlayer].getName() + " is " + players[currentPlayer].getPersonalScore());
        // aggiorno il personaleScore
        players[currentPlayer].updatePersonalScore();
        System.out.println("(Game updateScore) updated personal score for player " + players[currentPlayer].getName() + " is " + players[currentPlayer].getPersonalScore());
        // aggiorno il boardScore
        players[currentPlayer].setBoardScore(boardGoal.getScore(players[currentPlayer].getGrid()));

        if(!players[currentPlayer].getSharedGoal1Achieved())
        {
            int newPoint;
            System.out.println("(Game updateScore goal 1) old shared score for player " + players[currentPlayer].getName() + " is " + players[currentPlayer].getSharedScore());
            newPoint = sharedGoal[0].getScore(players[currentPlayer].getGrid());
            players[currentPlayer].increaseSharedScore(newPoint);
            System.out.println("(Game updateScore goal 1) new shared score for goal 1 for player " + players[currentPlayer].getName() + " is " + players[currentPlayer].getSharedScore());
            if (newPoint != 0)
                players[currentPlayer].setSharedGoal1AchievedToTrue();
        }

        // controllo se ha raggiunto il secondo sharedGoal
        if(!players[currentPlayer].getSharedGoal2Achieved())
        {
            int newPoint;
            System.out.println("(Game updateScore goal 2) old shared score for player " + players[currentPlayer].getName() + " is " + players[currentPlayer].getSharedScore());
            newPoint = sharedGoal[1].getScore(players[currentPlayer].getGrid());
            players[currentPlayer].increaseSharedScore(newPoint);
            System.out.println("(Game updateScore goal 2) new shared score for goal 1 for player " + players[currentPlayer].getName() + " is " + players[currentPlayer].getSharedScore());

            if (newPoint != 0)
                players[currentPlayer].setSharedGoal2AchievedToTrue();
        }
    }
    public int getPosition(String username){
        for(int i=0; i<players.length; i++){
            if(Objects.equals(username, players[i].getName())){
                return i;
            }
        }
        return -1;
    }

    public List<String> getPlayersUsername(){
        List<String> usernames = new ArrayList<>();
        for(int i=0; i<players.length; i++){
            usernames.add(players[i].getName());
        }
        return usernames;
    }
    public void fillBoard(){
        board.fill();
    }
}
