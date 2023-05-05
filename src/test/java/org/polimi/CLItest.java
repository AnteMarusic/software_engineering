package org.polimi;

import org.polimi.server.model.Board;
import org.polimi.server.model.Card;
import org.polimi.client.GameEnv;

import java.util.ArrayList;

public class CLItest {
    public static void main(String[] args){
        String[] players = new String[2];
        Board board = new Board(2);
        players[0] = new String("Pippo");
        players[1] = new String("Baudo");
        GameEnv gameEnv = new GameEnv(players, 0, 2);
        gameEnv.setClientBoard(board.getGrid());
        ArrayList<Card> cards = new ArrayList<Card>();
        cards.add(new Card(Card.Color.WHITE, Card.State.PICKABLE));
        cards.add(new Card(Card.Color.PINK, Card.State.PICKABLE));
        cards.add(new Card(Card.Color.CYAN, Card.State.PICKABLE));
        gameEnv.setChosenCards(cards);
        gameEnv.insert(0);
        cards.removeAll(cards);
        cards.add(new Card(Card.Color.ORANGE, Card.State.PICKABLE));
        cards.add(new Card(Card.Color.ORANGE, Card.State.PICKABLE));
        cards.add(new Card(Card.Color.BLUE, Card.State.PICKABLE));
        gameEnv.setChosenCards(cards);
        gameEnv.insert(3);
        gameEnv.printRoutine();
    }
}
