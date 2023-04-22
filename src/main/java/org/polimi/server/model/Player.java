package org.polimi.server.model;

import org.polimi.server.model.goal.PersonalGoal;

import java.util.ArrayList;
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
    /**
     * increases the personal score counter by the int value newPoints
     * @param newPoints The integer value indicates the number of points that need to be added to the personal score
     */
    private void increasePersonalScore(int newPoints){
        this.personalScore  += newPoints;
    }

    private void UpdatePersonalScore(){
        int score = personalGoal.scoreAchieved(bookshelf.getGrid());
        if(score > personalScore) {
            this.increasePersonalScore(score - personalScore);
        }
    }

    public Card[][] getGrid () {
        return bookshelf.getGrid();
    }

    /**
     * gets from std input one to three coordinates
     * ensures that the arrayList containing these coordinates is dim one to three and contains valid coordinates
     * ensures that the dimension of the arrayList isn't greater than maxInsertable in bookshelf
     * ensures that the cards are picked in a line from the board
     */
    public void chooseCards(Board board){
        //since the cards have to be picked in a line, each card picked has to have one constant coordinate
        int x=0, y=0;
        int previousX=0, previousY=0;
        Card tempCard = null;
        Coordinates coordinates = new Coordinates(0,0);
        Scanner scanner = new Scanner(System.in);
        int maxInsertable = bookshelf.getMaxInsertable();
        ArrayList<Card> chosenCards = new ArrayList<>(maxInsertable);
        int counter=0;
        String answer;
        while(counter < maxInsertable){
            if(counter > 0) {
                System.out.println("Do you want to choose another? You can choose another" + (maxInsertable - counter) + "cards\nType 'yes' or 'no'");
                answer=scanner.nextLine().toLowerCase();
                if(answer.equals("no"))
                    break;
                else if(!answer.equals("yes")){
                    System.out.println("Input is not valid, type again...\n");
                    continue;
                }
            }

            switch(counter){
                case 0 -> {
                    do {
                        System.out.println("Type row number (0 to 8)");
                        previousX = scanner.nextInt();
                        System.out.println("Type col number (0 to 8)");
                        previousY = scanner.nextInt();
                        coordinates.setRowCol(previousX,previousY);
                        if(coordinates.CoordinatesAreValid()){
                            tempCard = board.seeCardAtCoordinates(coordinates);
                            if (tempCard == null)
                                System.out.println("There's no card at that position, please choose another...");
                            else {
                                if(tempCard.getState() == Card.State.NOT_PICKABLE)
                                    System.out.println("That card cannot be picked, please choose another...");
                                else if(tempCard.getState() == Card.State.PICKABLE){
                                    chosenCards.add(board.getCardAtCoordinates(coordinates));
                                    counter++;
                                    System.out.println("You chose a card in position (" + previousX + "," + previousY);
                                }
                            }
                        }
                        else
                            System.out.println("These coordinates are not valid, choose again...");
                    }while(!coordinates.CoordinatesAreValid() || tempCard == null || tempCard.getState() != Card.State.PICKABLE);//first card
                    previousX = x;
                    previousY = y;
                }

                case 1 -> {
                    do {
                        System.out.println("Choose your next Card");
                        System.out.println("Type row number (0 to 8)");
                        x = scanner.nextInt();
                        System.out.println("Type col number (0 to 8)");
                        y = scanner.nextInt();
                        coordinates.setRowCol(x, y);
                        if (coordinates.CoordinatesAreValid() && ((x == previousX && (y == previousY+1 ||y == previousY-1)) || ((x == previousX+1 ||x == previousX-1) && y == previousY))) {
                            tempCard = board.seeCardAtCoordinates(coordinates);
                            if (tempCard == null)
                                System.out.println("There's no card at that position, please choose another...");
                            else {
                                chosenCards.add(board.getCardAtCoordinates(coordinates));
                                System.out.println("You chose a card in position (" + x + "," + y);
                            }
                        } else
                            System.out.println("These coordinates are not valid, choose again...");
                    } while (!coordinates.CoordinatesAreValid() || !((x == previousX && (y == previousY+1 ||y == previousY-1)) || ((x == previousX+1 ||x == previousX-1) && y == previousY)) || tempCard == null); //eventual second card
                    previousX = x;
                    previousY = y;
                }
                case 2 -> {
                    if (x == previousX) {
                        do {
                            System.out.println("Choose your next Card");
                            System.out.println("Type row number (0 to 8)");
                            x = scanner.nextInt();
                            System.out.println("Type col number (0 to 8)");
                            y = scanner.nextInt();
                            coordinates.setRowCol(x, y);
                            if (coordinates.CoordinatesAreValid() && ((x == previousX && y==previousY-1) || (x == previousX && y==previousY+1))) {
                                tempCard = board.getCardAtCoordinates(coordinates);
                                if (tempCard == null)
                                    System.out.println("There's no card at that position, please choose another...");
                                else {
                                    chosenCards.add(board.getCardAtCoordinates(coordinates));
                                    System.out.println("You chose a card in position (" + x + "," + y);
                                }
                            } else
                                System.out.println("These coordinates are not valid, choose again... ");
                        } while (!coordinates.CoordinatesAreValid() || !((x == previousX && y==previousY-1) || (x == previousX && y==previousY+1)) || tempCard == null);
                    }
                    if (y == previousY) {
                        do {
                            System.out.println("Choose your next Card");
                            System.out.println("Type row number (0 to 8)");
                            x = scanner.nextInt();
                            System.out.println("Type col number (0 to 8)");
                            y = scanner.nextInt();
                            coordinates.setRowCol(x, y);
                            if (coordinates.CoordinatesAreValid() && ((y == previousY && x==previousX-1) || (y == previousY && x==previousX+1))) {
                                tempCard = board.getCardAtCoordinates(coordinates);
                                if (tempCard == null)
                                    System.out.println("There's no card at that position, please choose another...");
                                else {
                                    chosenCards.add(board.getCardAtCoordinates(coordinates));
                                    System.out.println("You chose a card in position (" + x + "," + y);
                                }
                            } else
                                System.out.println("These coordinates are not valid, choose again...");
                        } while (!coordinates.CoordinatesAreValid() || !((y == previousY && x==previousX-1) || (y == previousY && x==previousX+1)) || tempCard == null);
                    }
                }
            }
            counter++;
        }
        if(counter>1)
            orderChosenCards(chosenCards);
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

    private void orderChosenCards(ArrayList<Card> toInsert) {
        ArrayList<Card> temp = new ArrayList<>(toInsert.size());
        Scanner scanner = new Scanner(System.in);
        int position;
        int i = 0;

        printChosenCards(toInsert);

        while (i < toInsert.size()) {
            System.out.println("Where do you want to put the card in position " + i +"?\n");
            position = scanner.nextInt();
            if (temp.get(position) != null) {
                if (position >= 0 && position < toInsert.size()) {
                    temp.add(toInsert.get(i));
                    i++;
                }
                else {
                    System.out.println(position + "is not in the interval [0, toInsert.length]...\n Please choose again\n");
                }
            }
            else
                System.out.println("There's already a card in position "+position+", choose another...");
        }
        printChosenCards(temp);
        insertInBookshelf(temp);
    }

    /**
     *
     * @param chosenCards requires that no card inside it is null
     */
    private void printChosenCards (ArrayList<Card> chosenCards) {
        chosenCards.forEach(System.out::println);
    }

    private void insertInBookshelf (ArrayList<Card> toInsert){
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
}
