package org.polimi.servernetwork.model;

import org.polimi.servernetwork.model.goal.PersonalGoal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Player implements Serializable {

    /**
     * true if bookshelf is full
     */
    boolean IsBookshelfFull = false;
    final private String name;
    /**
     * score obtained with SharedGoals
     */
    private int sharedScore;
    /**
     * score obtained with PersonalGoals
     */
    private int personalScore;
    private int boardScore;
    private int winPoint;
    private PersonalGoal personalGoal;
    /**
     * boolean that is true if the first shared goal is achieved.
     * Is necessary since points from that goal can be collected only once.
     */
    private boolean sharedGoal1Achieved;

    /**
     * boolean that is true if the second shared goal is achieved.
     * Is necessary since points from that goal can be collected only once.
     */
    private boolean sharedGoal2Achieved;
    /**
     * each player has its bookshelf
     */
    private final Bookshelf bookshelf;


    /**
     * builds a player without points, with no shared goal achieved and with an empty new bookshelf.
     * @param playerName is the name to give to the player
     */
    public Player(String playerName) {
        this.name = playerName;
        this.sharedScore = 0;
        this.personalScore= 0;
        this.winPoint = 0;
        this.sharedGoal1Achieved = false;
        this.sharedGoal2Achieved = false;
        this.bookshelf = new Bookshelf();
    }

    /**
     * @return String indicating player's name
     */
    public String getName(){
        return this.name;
    }

    /**
     * @return int indicating player's shared score
     */
    public int getSharedScore(){
        return this.sharedScore;
    }

    /**
     * @return int indicating player's personal score
     */
    public int getPersonalScore(){
        return this.personalScore;
    }
    /**
     * @return int indicating player's board score
     */
    public int getBoardScore() {
        return this.boardScore;
    }
    public void setBoardScore(int boardScore){
        this.boardScore = boardScore;
    }

    /**
     * @return boolean that is true if the first shared goal is achieved.
     */
    public boolean getSharedGoal1Achieved(){
        return this.sharedGoal1Achieved;
    }

    /**
     * @return boolean that is true if the second shared goal is achieved.
     */
    public boolean getSharedGoal2Achieved(){
        return this.sharedGoal2Achieved;
    }

    /**
     * game will use this functions if and only if shared goal 1 is achieved from the player
     */
    public void setSharedGoal1AchievedToTrue(){
        this.sharedGoal1Achieved = true;
    }

    /**
     * game will use this functions if and only if shared goal 2 is achieved from the player
     */
    public void setSharedGoal2AchievedToTrue(){
        this.sharedGoal2Achieved = true;
    }

    /**
     * increases the shared score counter by the int value newPoints
     * @param newPoints The integer value indicates the number of points that need to be added to the shared score
     */
    public void increaseSharedScore(int newPoints){
        this.sharedScore  += newPoints;
    }

    public void setPersonalGoal(PersonalGoal pg){
        this.personalGoal = pg;
    }
    public int getTotalScore(){
        return personalScore + sharedScore + boardScore + winPoint;
    }

    public void setWinPoint () {
        this.winPoint = 1;
    }
    /**
     * increases the personal score counter by the int value newPoints
     * @param newPoints The integer value indicates the number of points that need to be added to the personal score
     */
    private void increasePersonalScore(int newPoints){
        this.personalScore  += newPoints;
    }

    public void updatePersonalScore(){
        int score = personalGoal.getScore(bookshelf.getGrid());
        if(score > personalScore) {
            this.increasePersonalScore(score - personalScore);
        }
    }

    /**
     * for testing purposes
     * @return the array of coordinates
     */
    public Coordinates[] getPersonalGoalCoordinates() {
        return personalGoal.getCoordinates();
    }

    /**
     * for testing purposes
     * @return the array of colors
     */
    public Card.Color[] getPersonalGoalColors() {
        return personalGoal.getColors();
    }



    private void getCoordinatesFromSTDInput (boolean b, int x, int y, int previousX, int previousY, Board board, ArrayList<Card> chosenCards) {
        Scanner scanner = new Scanner(System.in);
        Coordinates coordinates;
        Card tempCard;


        System.out.println("Choose your next Card\n");
        System.out.println("Type row number (0 to 8)\n");
        x = scanner.nextInt();
        System.out.println("Type col number (0 to 8)\n");
        y = scanner.nextInt();
        coordinates = new Coordinates(x, y);
        if (b) {
            tempCard = board.seeCardAtCoordinates(coordinates);
            if (tempCard == null)
                System.out.println("There's no card at that position, please choose another...\n");
            else {
                chosenCards.add(board.getCardAtCoordinates(coordinates));
                System.out.println("You chose a card in position (" + x + "," + y + "\n");
            }
        } else
            System.out.println("These coordinates are not valid, choose again... \n");
    }


    public boolean getIsBookshelfFull(){
        return this.IsBookshelfFull;
    }
    public int getPersonalGoalIndex () {
        return personalGoal.getIndex();
    }

    public void insertInBookshelf (List<Card> toInsert, int column){
        bookshelf.insert(toInsert, column);       // va corretto insert
    }
    public boolean checkIfBookshelfIsFull(){
        return bookshelf.checkIfFull();
    }

    public Card[][] getGrid () {
        return bookshelf.getGrid();
    }
}
