package org.polimi;

import org.polimi.shared_goal.*;

public class Game {
    private Board board;
    private Player[] players;
    private BagOfCards bagOfCards;
    private AbstractSharedGoal SharedGoal1;
    private AbstractSharedGoal SharedGoal2;

    public Game(Player ... players) {
        this.players = players;
        this.bagOfCards = new BagOfCards();
    }

    public Board getBoard() {
        return this.board;
    }
}
