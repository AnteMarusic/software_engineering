package org.example;

public class Player {
    final private String name;
    private int sharedScore;
    private int personalGoal;
    private AbstractPersonalGoal personalGoal;

    private Bookshelf myShelfie;

    public Player(String playerName) {
        this.name = playerName;
        this.sharedScore = 0;
        this.personalGoal = 0;
    }

    public String getName(){
        return this.name;
    }

    public int getSharedScore(){
        return this.sharedScore;
    }

    public int getPersonalGoal(){
        return this.personalGoal;
    }

    public void updateSharedScore(int newPoints){
        this.sharedScore  += newPoints;
    }

    public void updatePersonalScore(int newPoints){
        this.personalGoal += newPoints;
    }

    //passes coordinates buffer to Game who will first check if them are
    //available and then eventually let him insert the correspondent cards
    //into his bookshelf
    public  Coordinates[] chooseCards(){
        Coordinates[] buffer= new Coordinates[3];
        //choose cards from GUI/CLI cards and assign it
        //to buffer
        return buffer;
    }

    //player has no access to Board, Game will pass him the cards.
    //here we are already sure that this cards are available to be inserted
    public void insertInBookshelf (Card[] buffer){
        int column;
        Coordinates AUXkey = new Coordinates();
        //choose column from GUI/CLI
        //reorder buffer as preferred
        for(int i=0 ; i<buffer.length && buffer[i]!=null; i++){
            //wait for bookshelf commit ...
        }
    }

    public void orderCards(Card[] buffer){
        //discuss about GUI/CLI
    }

}
