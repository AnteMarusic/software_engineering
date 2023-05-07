package org.polimi.server.model;

import org.polimi.server.model.goal.PersonalGoal;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class Player {

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
    private int totalScore;
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
    public void setTotalScore(int totalScore){
        this.totalScore = totalScore;
    }
    public void updateTotalscore (int punti){
        totalScore = totalScore + punti;
    }
    public int getTotalScore(){
        return totalScore;
    }
    /**
     * increases the personal score counter by the int value newPoints
     * @param newPoints The integer value indicates the number of points that need to be added to the personal score
     */
    private void increasePersonalScore(int newPoints){
        this.personalScore  += newPoints;
    }

    private void UpdatePersonalScore(){
        int score = personalGoal.getScore(bookshelf.getGrid());
        if(score > personalScore) {
            this.increasePersonalScore(score - personalScore);
        }
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

    public void insertInBookshelf (ArrayList<Card> toInsert){
        int col , insertable;
        Scanner scanner = new Scanner(System.in);

        do {
            do {
                System.out.println("Type in which column (o to 5) you want to insert the cards that you picked");
                col = scanner.nextInt();
            }while(0 <= col && 5 >= col);
            insertable = this.bookshelf.getInsertable(col);
            if(toInsert.size() >= insertable)
                System.out.println("There isn't enough space in that column, please choose another...");
        } while (toInsert.size() >= insertable);
        bookshelf.insert(toInsert, col);
        UpdatePersonalScore();
        if(bookshelf.CheckIfFull())
            IsBookshelfFull = true;
    }

    public boolean getIsBookshelfFull(){
        return this.IsBookshelfFull;
    }
    public Map<Coordinates, Card> getBookshelf () {
        return bookshelf.getBookshelf();
    }
    public int getPersonalGoalIndex () {
        return personalGoal.getIndex();
    }
}
