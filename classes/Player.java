package org.example;

import java.util.Scanner;

public class Player {
    final private String name;
    private int sharedScore;
    private int personalScore;
    private PersonalGoal personalGoal;

    //boolean which represents if the shared goal 1 is already achieved or not
    private boolean sharedGoal1Bool;

    //boolean which represents if the shared goal 2 is already achieved or not
    private boolean sharedGoal2Bool;

    private Bookshelf bookshelf;

    public Player(String playerName) {
        this.name = playerName;
        this.sharedScore = 0;
        this.personalScore= 0;
        this.sharedGoal1Bool = false;
        this.sharedGoal2Bool = false;
        this.bookshelf = new Bookshelf();
    }

    public String getName(){
        return this.name;
    }

    public int getSharedScore(){
        return this.sharedScore;
    }

    public int getPersonalScore(){
        return this.personalScore;
    }


    public boolean getSharedScore1Bool(){
        return this.sharedGoal1Bool;
    }

    public boolean getSharedScore2Bool(){
        return this.sharedGoal2Bool;
    }

    //game will use this functions if and only if shared goal 1 is achieved from
    //the player
    public void setSharedGoal1Bool(){
        this.sharedGoal1Bool = true;
    }

    //game will use this functions if and only if shared goal 2 is achieved from
    //the player
    public void setSharedGoal2Bool(){
        this.sharedGoal2Bool = true;
    }

    public void updateSharedScore(int newPoints){
        this.sharedScore  += newPoints;
    }

    public void updatePersonalScore(int newPoints){
        this.personalScore  += newPoints;
    }

    public void updatePersonalScore(){
        if(personalGoal.scoreAchieved() > personalScore) {
            this.updatePersonalScore(personalGoal.scoreAchieved() - personalScore);
        }
    }

    //passes coordinates buffer to Game who will first check if them are
    //available and then eventually let him insert the correspondent cards
    //into his bookshelf
    public Coordinates[] chooseCards(){
        int x,y;
        int exit=0;
        Coordinates[] AUXcoor= new Coordinates[3];
        Scanner scanner = new Scanner(System.in);
        for(int i=0 ; i<3 || exit==1; i++){
            System.out.println("Inserire numero riga\n");
            x = scanner.nextInt();
            System.out.println("Inserire numero colonna\n");
            y = scanner.nextInt();
            AUXcoor[i] = new Coordinates(x,y);
            System.out.println("1 per scegliere un'altra carta, 0 per confermare\n");
            exit = scanner.nextInt();
        }
        return AUXcoor;
    }

    //player has no access to Board, Game will pass him the cards.
    //here we are already sure that this cards are available to be inserted
    public boolean insertInBookshelf (Buffer buffer){
        int col , insertable;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digitare numero colonna in cui si vuole inserire le carte\n");
        col = scanner.nextInt();
        insertable = this.bookshelf.getInsertable(col);
        if(buffer.getBuffer().length <= insertable) {
            this.bookshelf.insert(buffer, col);
            return true;
        }else{
            return false;
        }
    }

    public void orderCards(Buffer buffer){
        int[] indexOrder = new int [buffer.getBuffer().length];
        Scanner scanner = new Scanner(System.in);
        bookshelf.print();
        System.out.println(buffer);
        for(int i=0 ; i<buffer.getBuffer().length ; i++){
            indexOrder[i] = scanner.nextInt();
        }
        buffer.order(indexOrder);
    }

}
