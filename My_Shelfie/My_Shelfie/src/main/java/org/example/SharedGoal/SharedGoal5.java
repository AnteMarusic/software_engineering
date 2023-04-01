package org.example.SharedGoal;

import org.example.Bookshelf;
import org.example.Card;

//5th SHARED GOAL, AT LEAST 3 COLUMNS WITH NO MORE THAN 3 COLORS
public class SharedGoal5 extends AbstractSharedGoal {
    public SharedGoal5(int numOfPlayer) {
        super(numOfPlayer);
    }

    @Override
    public boolean achieved(Bookshelf bookshelf) {
        Card[][] tmp = bookshelf.getGrid();
        int count = 0;
        int numOfColors;
        Card [] currColors = new Card[3];
        currColors [0] = tmp[0][0];
        int currColorsIndex = 1;
        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < 5; i++) {
                for(int k=0 ; k<currColorsIndex ; k++){
                    if(tmp[i][j].getColor() != )
                }
            }
            numOfColors = 0;
        }
        return count>=3;
    }
}
