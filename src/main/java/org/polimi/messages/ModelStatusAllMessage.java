package org.polimi.messages;

import org.polimi.server.model.Card;
import org.polimi.server.model.Coordinates;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ModelStatusAllMessage extends Message implements Serializable {
    private final Map<Coordinates, Card> board;
    private final Map<Coordinates, Card> bookshelf;
    private final int sharedGoal1;
    private final int sharedGoal2;
    private final int personalGoal;
    private final String[] usernames;

    public ModelStatusAllMessage(String username, Map<Coordinates, Card> board, Map<Coordinates, Card> bookshelf, int sharedGoal1, int sharedGoal2, int personalGoal, String[] usernames) {
        super(username, MessageType.MODEL_STATUS_ALL);
        this.board = board;
        this.bookshelf = bookshelf;
        this.sharedGoal1 = sharedGoal1;
        this.sharedGoal2 = sharedGoal2;
        this.personalGoal = personalGoal;
        this.usernames = usernames;
    }

    public HashMap<Coordinates, Card> getBoard() {
        return board;
    }

    public HashMap<Coordinates, Card> getBookshelf() {
        return bookshelf;
    }

    public int getSharedGoal1() {
        return sharedGoal1;
    }

    public int getSharedGoal2() {
        return sharedGoal2;
    }

    public int getPersonalGoal() {
        return personalGoal;
    }

    public String[] getUsernames() {
        return usernames;
    }
}
