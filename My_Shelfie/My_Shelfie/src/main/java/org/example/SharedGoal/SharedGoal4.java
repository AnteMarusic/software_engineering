package org.example.SharedGoal;

import org.example.Bookshelf;
import org.example.Card;

//4th SHARED GOAL, two 2x2 matrices with same colors
public class SharedGoal4 extends AbstractSharedGoal{
    public SharedGoal4(int numOfPlayer) {
        super(numOfPlayer);
    }

    @Override
    public boolean achieved(Bookshelf bookshelf){
        Card [][] tmp = bookshelf.getGrid();
        int count=0;
        for(int i=0 ; i<4 ; i++){
            for(int j=0 ; j<3 ; j++){
                if(tmp[i][j].getColor() == tmp[i+1][j].getColor() && tmp[i][j].getColor() == tmp[i+1][j+1].getColor() && tmp[i][j].getColor() == tmp[i][j+1].getColor()) {
                    count++;
                }
            }
        }
        return count >= 2;
    }
}
