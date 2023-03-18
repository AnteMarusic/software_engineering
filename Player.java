package org.example;

public class Player {
    private String name;
    private BookShelf bookShelf;
    private PersonalGoal personalGoalOne;
    private PersonalGoal personalGoalTwo;
    private int score;

    public String getName() {
        return this.name;
    }

    public Player(String name, BookShelf bookShelf) {
        this.name = name;
        this.bookShelf = bookShelf;
        this.score = 0;
    }

    public void playerProcedure () {
        //
    }

}
