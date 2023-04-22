package org.polimi.server.model.shared_goal;

import org.polimi.server.model.Card;

//2nd SHARED GOAL, SAME COLOR IN ALL 4 CORNERS
public class SharedGoal2 extends AbstractSharedGoal{
    public SharedGoal2(int numOfPlayer) {
        super(numOfPlayer);
    }

    @Override
    protected boolean achieved(Card[][] tmpGrid) {
        return (tmpGrid[0][0].getColor() == tmpGrid[5][4].getColor() &&
                tmpGrid[0][0].getColor() == tmpGrid[0][4].getColor() &&
                tmpGrid[0][0].getColor() == tmpGrid[5][0].getColor()
        );
    }
}
