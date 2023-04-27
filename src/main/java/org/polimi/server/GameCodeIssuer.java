package org.polimi.server;

import org.polimi.server.model.Game;
import org.polimi.server.model.Player;

import java.util.*;

public class GameCodeIssuer {
    private final Map<Integer, Game> associations;
    private final List<Integer> freedIdCodes;
    private int currentHighestCode;

    public GameCodeIssuer () {
        this.associations = new HashMap<>();
        this.freedIdCodes = new LinkedList<>();
        this.currentHighestCode = 0;
    }

    public synchronized boolean containsIdCode (int idCode) {return associations.containsKey(idCode);}
    public synchronized Game getGameAssociatedWith (int idCode) throws NoSuchElementException {
        if (!associations.containsKey(idCode))
            throw new NoSuchElementException();
        else {
            return associations.get(idCode);
        }
    }
    public synchronized int associateCodeTo (Game game) throws NullPointerException {
        if (game == null)
            throw new NullPointerException();
        else {
            if (!freedIdCodes.isEmpty()) {
                int temp = freedIdCodes.get(0);
                associations.put(temp, game);
                return temp;
            }
            else {
                currentHighestCode ++;
                associations.put(currentHighestCode, game);
                return currentHighestCode;
            }
        }
    }
    public synchronized void freeCodeAssociatedWith (Game game) throws NullPointerException, NoSuchElementException{
        if (game == null) throw new NullPointerException();
        else if (!associations.containsValue(game)) throw new NoSuchElementException();
        else {
            int key = associations.keySet().parallelStream().filter((x) -> associations.get(x).equals(game)).findFirst().orElse(-1);
            associations.remove(key);
            freedIdCodes.add(key);
        }
    }

    public static void main (String[] args) {
        GameCodeIssuer g = new GameCodeIssuer();
        System.out.println(g.associateCodeTo(new Game(new Player[1])));

    }


}
