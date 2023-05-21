package org.polimi.servernetwork.model.goal.shared_goal;

import org.polimi.servernetwork.model.Card;
import org.polimi.servernetwork.model.goal.Goal;

import java.util.Stack;

public abstract class AbstractSharedGoal implements Goal {
    /**
     * const values representing the size of the card[][] Bookshelf
     */
    protected static final int ROW = 6;
    protected static final int COL = 5;
    protected static final int NUMOFCOLORS = 6;
    /**
     * stack of points. The content varies based on the number of player
     */
    private Stack <Integer> pointStack;

    /**
     * creates a stack of points that is different based on the numOfPlayers
     * @param numOfPlayer belongs to the interval [2,4]
     */
    public AbstractSharedGoal(int numOfPlayer){
        pointStack = new Stack<Integer>();
        if(numOfPlayer >= 2){
            pointStack.push(2);
            pointStack.push(4);
            if(numOfPlayer >=3){
                pointStack.push(6);
            }
            if(numOfPlayer == 4){
                pointStack.push(8);
            }
        }
    }

    /**
     * method to be implemented in the subclasses (strategy pattern)
     * @param tmpGrid a Card[][] that has the properties of a bookshelf object
     * @return true
     */
    protected abstract boolean achieved(Card[][] tmpGrid);

    @Override
    public int getScore(Card[][] tmpGrid){
        if(achieved(tmpGrid)){
            int x = pointStack.pop();
            return x;
        }else{
            return 0;
        }
    }

}


//2nd SHARED GOAL, SAME COLOR IN ALL 4 CORNERS