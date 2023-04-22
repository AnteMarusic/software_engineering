package org.polimi.server.model.shared_goal;

import org.polimi.server.model.Card;

import java.util.Stack;

public abstract class AbstractSharedGoal {
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

    public int calcScore(Card[][] tmpGrid){
        if(achieved(tmpGrid)){
            int x = pointStack.pop();
            return x;
        }else{
            return 0;
        }
    }

}


//2nd SHARED GOAL, SAME COLOR IN ALL 4 CORNERS