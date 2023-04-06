package org.polimi.shared_goal;

import org.polimi.Bookshelf;
import org.polimi.Card;
import java.util.Stack;

public abstract class AbstractSharedGoal {
    private Stack<Integer> pointStack;

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