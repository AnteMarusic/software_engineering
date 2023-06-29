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
    private int sharedScore1;
    private int sharedScore2;
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
        this.boardScore = 0 ;
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
    public int getSharedScore1(){
        return this.sharedScore1;
    }
    public int getSharedScore2(){
        return this.sharedScore2;
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

    public int getWinPoint(){
        return this.winPoint;
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
        System.out.println("(Player setSharedGoal1) setto che ho fatto lo shared goal 1");
        this.sharedGoal1Achieved = true;
    }

    /**
     * game will use this functions if and only if shared goal 2 is achieved from the player
     */
    public void setSharedGoal2AchievedToTrue(){
        System.out.println("(Player setSharedGoal2) setto che ho fatto lo shared goal 2");
        this.sharedGoal2Achieved = true;
    }

    /**
     * increases the shared score counter by the int value newPoints
     * @param newPoints The integer value indicates the number of points that need to be added to the shared score
     */
    public void increaseSharedScore(int newPoints){
        this.sharedScore  += newPoints;
    }
    public void setSharedScore1(int points){
        sharedScore1 = points;
    }
    public void setSharedScore2(int points){
        sharedScore2 = points;
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

    /**
     * Updates the personal score based on the current state of the bookshelf and personal goal.
     * If the calculated score is higher than the current personal score, it increases the personal score.
     */
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

    public void printBookshelf(){
        bookshelf.printbookshelf();
    }
}
