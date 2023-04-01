package org.example.SharedGoal;

import org.example.Bookshelf;
import org.example.Card;

//3rd SHARED GOAL, AT LEAST 4 COLUMN OF 4 SAME COLOR CARDS
public class SharedGoal3 extends AbstractSharedGoal{

    public SharedGoal3(int numOfPlayer) {
        super(numOfPlayer);
    }

    @Override
    public boolean achieved(Bookshelf bookshelf) {
        Card [][] tmp = bookshelf.getGrid();
        int count=0;
        for(int i=0 ; i<2 ; i++){
            for(int j=0 ; j<4 ; j++){
                if(tmp[i][j].getColor() == tmp[i+1][j].getColor() && tmp[i+1][j].getColor() == tmp[i+2][j].getColor() &&tmp[i+2][j].getColor() == tmp[i+3][j].getColor()){
                    count++;
                }
            }
        }
        return count>=4;
    }
}
