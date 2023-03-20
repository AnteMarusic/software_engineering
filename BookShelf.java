package org.example;

import java.util.ArrayList;

public class BookShelf {
    private Card[][] bookShelf;
    private ArrayList<SharedGoal> SharedGoalList;
    private PersonalGoal personalGoal;
    private boolean full;


    public void add (Buffer f) {
        this.notifyAll();
    }

    public BookShelf (SharedGoal sg1, SharedGoal sg2, PersonalGoal pg) {
        this.attachPersonalGoal(pg);
        this.attachSharedGoal(sg1);
        this.attachSharedGoal(sg2);
    }

    private void attachSharedGoal(SharedGoal g) {
        SharedGoalList.add(g);
    }

    private void detachSharedGoal(SharedGoal g) {
        SharedGoalList.remove(g);
    }

    private void attachPersonalGoal(PersonalGoal g) {
        personalGoal = g;
    }

    private void detachPersonalGoal(PersonalGoal g) {
        personalGoal = null;
    }

    public Card[][] getGrid () {
        Card[][] miao = new Card[6][5];
        for(int i=0; i<6; i++){
            for(int j=0; j<5; j++){
                miao[i][j]=bookShelf[i][j];
            }
        }
        return miao;
    }

    public void notifyAll () {
        personalGoal.achieved(this);
    }


}
