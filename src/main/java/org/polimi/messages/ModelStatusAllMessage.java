package org.polimi.messages;

import org.polimi.server.model.Card;
import org.polimi.server.model.Coordinates;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class ModelStatusAllMessage extends Message implements Serializable {
    private final Map<Coordinates, Card> board;
    private final List<Card[][]> bookshelves;
    private final int sharedGoal1;
    private final int sharedGoal2;
    private final int personalGoal;
    private final List<String> usernames;

    public ModelStatusAllMessage(String username, Map<Coordinates, Card> board, List<Card[][]> bookshelf, int sharedGoal1, int sharedGoal2, int personalGoal, List<String> usernames) {
        super(username, MessageType.MODEL_STATUS_ALL);
        this.board = board;
        this.bookshelves = bookshelf;
        this.sharedGoal1 = sharedGoal1;
        this.sharedGoal2 = sharedGoal2;
        this.personalGoal = personalGoal;
        this.usernames = usernames;
    }

    public Map<Coordinates, Card> getBoard() {
        return board;
    }

    public List<Card[][]> getBookshelves() {
        return bookshelves;
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

    public List<String> getUsernames() {
        return usernames;
    }

    @Override
    public String toString() {
        return "ModelStatusAllMessage{" +
                "usernames=" + usernames +
                '}';
    }
}
