package org.polimi.shared_goal;

import org.polimi.Bookshelf;
import org.polimi.Card;

//1st SHARED GOAL, X6 VERTICAL EQUAL COUPLES
public class SharedGoal1 extends AbstractSharedGoal {
    public SharedGoal1(int numOfPlayer) {
        super(numOfPlayer);
    }

    @Override
    public boolean achieved(Bookshelf bookshelf) {
        Card[][] tmp = bookshelf.getGrid();
        int count = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (tmp[i][j].getColor() == tmp[i + 1][j].getColor()) {
                    count++;
                }
            }
        }
        return count >= 6;
    }
}
