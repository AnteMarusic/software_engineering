package org.polimi;

import org.polimi.personal_goal.PersonalGoal;
import org.polimi.Card.State;

import java.util.Scanner;

public class Player {

    boolean bookFull=false; //true if bookshlef is full
    final private String name;
    /**
     * score obtained with SharedGoals
     */
    private int sharedScore;
    /**
     * score obtained with PersonalGoals
     */
    private int personalScore;
    public PersonalGoal personalGoal;
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
    private Bookshelf bookshelf;


    /**
     * builds a player without points, with no shared goal achieved and with an empty new bookshelf.
     * @param playerName
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
     * @return String indicating player's shared score
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
     * @param newPoints
     */
    public void increaseSharedScore(int newPoints){
        this.sharedScore  += newPoints;
    }

    public void setPersonalGoal(PersonalGoal pg){
        this.personalGoal = pg;
    }
    /**
     * increases the personal score counter by the int value newPoints
     * @param newPoints
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
     * ensures that the array is dim one to three and contains valid coordinates
     * ensures that the dimension of the array isn't bigger than maxInsertable in bookshelf
     * ensures that the cards are picked in a line from the board
     * missing : would be cleaner if the board notify us if once a card is picked there aren't
     * any more cards you can pick (because the card is in an isolated position in the board)
     */
    /*public void chooseCards(){
        //since the cards have to be picked in a line, each card picked has to have one constant coordinate
        int firstX = 0, firstY = 0;
        int coordinateToRemainConstant = 0;
        boolean flag;
        int x,y;
        int i = 0;
        int max = bookshelf.getMaxInsertable();
        int exit = 0;
        Card[] chosenCards = new Card[max];
        Board board = game.getBoard();
        Card temp;
        Scanner scanner = new Scanner(System.in);

        while (i < max && exit == 0){
            flag = true;
            System.out.println("Type row number (0 to ...)\n");
            x = scanner.nextInt();
            System.out.println("Type col number (0 to ...)\n");
            y = scanner.nextInt();
            temp = board.getCardAtCoordinate(new Coordinates(x, y));

            if (temp != null) {
                if (i == 0) {
                    firstX = x;
                    firstY = y;
                }
                else {
                    if(i == 1 && x != firstX && y != firstY) {
                        flag = false;
                    }
                    else {
                        if (flag && x == firstX) {
                            coordinateToRemainConstant = 0;
                        }
                        else if (flag && y == firstY){
                            coordinateToRemainConstant = 1;
                        }
                    }

                    if (i == 2){
                        if (coordinateToRemainConstant == 0 && x != coordinateToRemainConstant) {
                            flag = false;
                        }
                        if (coordinateToRemainConstant == 1 && y != coordinateToRemainConstant ) {
                            flag = false;
                        }
                    }
                }
                if (flag) {
                    chosenCards[i] = temp;
                    System.out.println("0 to pick another card, 1 to confirm\n");
                    exit = scanner.nextInt();
                    i++;
                }
            }
        }
        orderChoosenCards(chosenCards);
    } */

    public void chooseCards(){
        //since the cards have to be picked in a line, each card picked has to have one constant coordinate
        int x=0, y=0;
        int PreviousX=0, PreviousY=0;
        Card tempCard = null;
        Coordinates coor = new Coordinates(0,0);
        Scanner scanner = new Scanner(System.in);
        int maxInsertable = bookshelf.getMaxInsertable();
        Card[] chosenCards = new Card[maxInsertable];
        int counter=0;
        Board board = game.getBoard();
        while(counter < maxInsertable){
            if(counter>0){
                System.out.println("Do you want to choose another? You can choose another" + (maxInsertable-counter) + "cards\nType 'yes' or 'no'\n");
                if(scanner.nextLine().equals("no"))
                    break;
            }
            switch(counter){
                case 0 -> {
                do {
                    System.out.println("Type row number (0 to ...)\n");
                    PreviousX = scanner.nextInt();
                    System.out.println("Type col number (0 to ...)\n");
                    PreviousY = scanner.nextInt();
                    coor.setXY(PreviousX,PreviousY);
                    if(coor.CoordsAreValid()){
                        tempCard = board.getCardAtCoordinate(coor);
                        if (tempCard == null)
                            System.out.println("There's no card at that position, please choose another...\n");
                        else {
                            if(tempCard.getState() != State.PICKABLE)
                                System.out.println("That card cannot be picked, please choose another...\n");
                            else {
                                chosenCards[counter]=tempCard;
                                System.out.println("You chose a card in position (" + PreviousX + "," + PreviousY + "\n");
                            }
                        }
                    }
                    else
                        System.out.println("These coordinates are not valid, choose again... \n");
                }while(!coor.CoordsAreValid() || tempCard == null || tempCard.getState() != State.PICKABLE);//first card
            }
                case 1 -> {
                    do {
                    System.out.println("Choose your next Card\n");
                    System.out.println("Type row number (0 to ...)\n");
                    x = scanner.nextInt();
                    System.out.println("Type col number (0 to ...)\n");
                    y = scanner.nextInt();
                    coor.setXY(x, y);
                    if (coor.CoordsAreValid() && ((x == PreviousX && (y == PreviousY+1 ||y == PreviousY-1)) || ((x == PreviousX+1 ||x == PreviousX-1) && y == PreviousY))) {
                        tempCard = board.getCardAtCoordinate(coor);
                        if (tempCard == null)
                            System.out.println("There's no card at that position, please choose another...\n");
                        else {
                            chosenCards[counter]=tempCard;
                            System.out.println("You chose a card in position (" + x + "," + y + "\n");
                        }
                    } else
                        System.out.println("These coordinates are not valid, choose again... \n");
                } while (!coor.CoordsAreValid() || !((x == PreviousX && (y == PreviousY+1 ||y == PreviousY-1)) || ((x == PreviousX+1 ||x == PreviousX-1) && y == PreviousY)) || tempCard == null); //eventual second card
                }
                case 2 -> {
                    if(x == PreviousX){
                    do {
                        System.out.println("Choose your next Card\n");
                        System.out.println("Type row number (0 to ...)\n");
                        x = scanner.nextInt();
                        System.out.println("Type col number (0 to ...)\n");
                        y = scanner.nextInt();
                        coor.setXY(x, y);
                        if (coor.CoordsAreValid() && ((x == PreviousX && y==PreviousY-1) || (x == PreviousX && y==PreviousY+1))) {
                            tempCard = board.getCardAtCoordinate(coor);
                            if (tempCard == null)
                                System.out.println("There's no card at that position, please choose another...\n");
                            else {
                                chosenCards[counter]=tempCard;
                                System.out.println("You chose a card in position (" + x + "," + y + "\n");
                            }
                        } else
                            System.out.println("These coordinates are not valid, choose again... \n");
                    } while (!coor.CoordsAreValid() || !((x == PreviousX && y==PreviousY-1) || (x == PreviousX && y==PreviousY+1)) || tempCard == null);
                }
                    if(y == PreviousY){
                        do {
                            System.out.println("Choose your next Card\n");
                            System.out.println("Type row number (0 to ...)\n");
                            x = scanner.nextInt();
                            System.out.println("Type col number (0 to ...)\n");
                            y = scanner.nextInt();
                            coor.setXY(x, y);
                            if (coor.CoordsAreValid() && ((y == PreviousY && x==PreviousX-1) || (y == PreviousY && x==PreviousX+1))) {
                                tempCard = board.getCardAtCoordinate(coor);
                                if (tempCard == null)
                                    System.out.println("There's no card at that position, please choose another...\n");
                                else {
                                    chosenCards[counter]=tempCard;
                                    System.out.println("You chose a card in position (" + x + "," + y + "\n");
                                }
                            } else
                                System.out.println("These coordinates are not valid, choose again... \n");
                        } while (!coor.CoordsAreValid() || !((y == PreviousY && x==PreviousX-1) || (y == PreviousY && x==PreviousX+1)) || tempCard == null);
                    }}
            }
            counter++;
        }
        if(counter>1)
            orderChoosenCards(chosenCards);
    }



    private void orderChoosenCards (Card[] toInsert) {
        Card[] temp = new Card[toInsert.length];
        Scanner scanner = new Scanner(System.in);
        int position;
        int i = 0;

        printChosenCards(toInsert);

        while (i < toInsert.length) {
            System.out.println("where do you want to put the card in position " + i);
            position = scanner.nextInt();
            if(temp[position]!=null){
                if (position >= 0 && position < toInsert.length) {
                    temp[position] = toInsert[i];
                    i++;
                }
                else {
                    System.out.println(i + "is not in the interval [0, toInsert.length]");
                }
            }
            else
                System.out.println("There's already a card in position "+position+", choose another position...");
        }
        printChosenCards(temp);
        insertInBookshelf(temp);
    }

    /**
     *
     * @param chosenCards requires that no card inside it is null
     */
    private void printChosenCards (Card[] chosenCards) {
        for (int i = 0; i < chosenCards.length; i++) {
            System.out.println(chosenCards[i]);
        }
    }

    //player has no access to Board, Game will pass him the cards.
    //here we are already sure that this cards are available to be inserted

    private void insertInBookshelf (Card[] toInsert){
        int col , insertable;
        Scanner scanner = new Scanner(System.in);

        do {
            System.out.println("Type column (o to 5) where you want to insert the cards you picked\n");
            col = scanner.nextInt();
            insertable = this.bookshelf.getInsertable(col);
        } while (toInsert.length > insertable);
        bookshelf.insert(toInsert, col);
        UpdatePersonalScore();
        if(bookshelf.CheckIfFull())
            bookFull=true;
    }

    public boolean getBookFull(){
        return this.bookFull;
    }


}
