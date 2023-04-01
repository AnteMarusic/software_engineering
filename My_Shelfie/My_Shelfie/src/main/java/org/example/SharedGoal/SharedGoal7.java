package org.example.SharedGoal;

import org.example.Bookshelf;
import org.example.Card;

//7th SHARED GOAL, at least 5 cards as a diagonal in every direction
public class SharedGoal7 extends AbstractSharedGoal{
    public SharedGoal7 (int numOfPlayer) {
        super(numOfPlayer);
    }

    @Override
    public boolean achieved(Bookshelf bookshelf){
        Card [][] tmp = bookshelf.getGrid();
        if(     (tmp[0][0].getColor() == tmp[1][1].getColor() && tmp[0][0].getColor() == tmp[2][2].getColor() && tmp[0][0].getColor() == tmp[3][3].getColor() && tmp[0][0].getColor() == tmp[4][4].getColor()) ||
                (tmp[1][0].getColor() == tmp[2][1].getColor() && tmp[1][0].getColor() == tmp[3][2].getColor() && tmp[1][0].getColor() == tmp[4][3].getColor() && tmp[1][0].getColor() == tmp[5][4].getColor()) ||
                (tmp[0][4].getColor() == tmp[1][3].getColor() && tmp[0][4].getColor() == tmp[2][2].getColor() && tmp[0][4].getColor() == tmp[3][1].getColor() && tmp[0][4].getColor() == tmp[4][0].getColor()) ||
                (tmp[1][4].getColor() == tmp[2][3].getColor() && tmp[1][4].getColor() == tmp[3][2].getColor() && tmp[0][4].getColor() == tmp[4][1].getColor() && tmp[0][4].getColor() == tmp[5][0].getColor())
        ){
          return true;
        }else{
            return false;
        }
    }
}