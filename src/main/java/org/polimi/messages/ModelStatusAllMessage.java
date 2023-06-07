package org.polimi.messages;

import org.polimi.servernetwork.model.Card;
import org.polimi.servernetwork.model.Coordinates;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class ModelStatusAllMessage extends Message implements Serializable {
    private final Map<Coordinates, Card> board;
    private final List<Card[][]> bookshelves;
    private final int sharedGoal1;
    private final int sharedGoal2;
    private final Coordinates[] personalGoalCoordinates;
    private final Card.Color[] personalGoalColors;
    private final List<String> usernames;

    public ModelStatusAllMessage(String username, Map<Coordinates, Card> board, List<Card[][]> bookshelf, int sharedGoal1, int sharedGoal2, Coordinates[] personalGoalCoordinates, Card.Color[] personalGoalColors, List<String> usernames) {
        super(username, MessageType.MODEL_STATUS_ALL);
        this.board = board;
        this.bookshelves = bookshelf;
        this.sharedGoal1 = sharedGoal1;
        this.sharedGoal2 = sharedGoal2;
        this.personalGoalColors = personalGoalColors;
        this.personalGoalCoordinates = personalGoalCoordinates;
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

    public Coordinates[] getPersonalGoalCoordinates() {
        return personalGoalCoordinates;
    }
    public Card.Color[] getPersonalGoalColors() {
        return personalGoalColors;
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
