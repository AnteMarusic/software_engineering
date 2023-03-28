package org.example;

import java.util.Stack;

public abstract class AbstractSharedGoal {
    Stack<Integer> pointStack;

    public AbstractSharedGoal(int numOfPlayer){
        pointStack = new Stack<Integer>();
        switch (numOfPlayer) {
            case 2 -> {
                pointStack.push(2);
                pointStack.push(4);
            }
            case 3 -> {
                pointStack.push(2);
                pointStack.push(4);
                pointStack.push(6);
            }
            case 4 -> {
                pointStack.push(2);
                pointStack.push(4);
                pointStack.push(6);
                pointStack.push(8);
            }
        }
    }

    public abstract boolean achieved(Bookshelf bookshelf);

    public int calcScore(Bookshelf bookshelf){
        if(achieved(bookshelf)){
            int x = pointStack.pop();
            return x;
        }else{
            return 0;
        }
    }

}
