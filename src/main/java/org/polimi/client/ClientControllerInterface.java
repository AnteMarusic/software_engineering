package org.polimi.client;

import org.polimi.messages.Message;
import org.polimi.servernetwork.model.Card;
import org.polimi.servernetwork.model.Coordinates;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public interface ClientControllerInterface {
    public void setUsername (String username);
    public Message handleMessage (Message message) throws IOException;
    public Message chooseUsername ();
    public void alreadyTakenUsername ();
    public Message chooseGameMode ();
    public Message chooseCards();
    public LinkedList<Coordinates> orderChosenCards(List<Coordinates> chosenCoordinates);
    public Message chooseColumn ();
    public void errorMessage ();
    public void newPlayerJoinedLobby (String newPlayer);
    public void loginSuccessful ();
    public void reconnectionSuccessful ();
    public void modelAllMessage (Map<Coordinates, Card> board, List<Card[][]> bookshelves, int sharedGoal1, int sharedGoal2, Coordinates[] personalGoalCoordinates, Card.Color[] personalGoalColors, List<String> usernames);
    public void disconnect();
}
