package org.polimi.client;

import org.polimi.messages.Message;
import org.polimi.servernetwork.model.Card;
import org.polimi.servernetwork.model.Coordinates;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public interface ClientControllerInterface {
    void setUsername (String username);
    Message handleMessage (Message message) throws IOException;
    Message chooseUsername ();
    void alreadyTakenUsername ();
    Message chooseGameMode ();
    Message chooseCards();
    ArrayList<Coordinates> orderChosenCards(List<Coordinates> chosenCoordinates);
    Message chooseColumn ();
    void loginSuccessful ();
    void reconnectionSuccessful ();
    void modelAllMessage (Map<Coordinates, Card> board, List<Card[][]> bookshelves, int sharedGoal1, int sharedGoal2, Coordinates[] personalGoalCoordinates, Card.Color[] personalGoalColors, List<String> usernames, int personalGoal, int currPlayer);
    void disconnect();
}
