package org.polimi.servernetwork.controller;

import java.util.*;

public class GameCodeIssuer {
    private final Map<Integer, GameController> associations;
    private List<Integer> readyToUsePrivateCode;
    private final List<Integer> freedIdCodes;
    private int currentHighestCode;

    public GameCodeIssuer () {
        this.associations = new HashMap<>();
        this.freedIdCodes = new LinkedList<>();
        this.currentHighestCode = 0;
    }

    public synchronized boolean containsIdCode (int idCode) {return associations.containsKey(idCode);}
    public synchronized GameController getGameController(int idCode) throws NoSuchElementException {
        if (!associations.containsKey(idCode))
            throw new NoSuchElementException();
        else {
            return associations.get(idCode);
        }
    }
    public synchronized int associateCodeTo (GameController gameController) throws NullPointerException {
        if (gameController == null)
            throw new NullPointerException();
        else {
            if (!freedIdCodes.isEmpty()) {
                int temp = freedIdCodes.get(0);
                associations.put(temp, gameController);
                return temp;
            }
            else {
                /*do
                currentHighestCode ++;
                while(alreadyExistGameCode(currentHighestCode));       // controllo non esista già un private game con lo stesso codice
                 */
                currentHighestCode ++;
                associations.put(currentHighestCode, gameController);
                return currentHighestCode;
            }
        }
    }
    public synchronized void freeCodeAssociatedWith (GameController gameController) throws NullPointerException, NoSuchElementException{
        if (gameController == null) throw new NullPointerException();
        else if (!associations.containsValue(gameController)) throw new NoSuchElementException();
        else {
            int key = associations.keySet().parallelStream().filter((x) -> associations.get(x).equals(gameController)).findFirst().orElse(-1);
            associations.remove(key);
            freedIdCodes.add(key);
        }
    }

    /*
    public static void main (String[] args) {
        GameCodeIssuer g = new GameCodeIssuer();
        System.out.println(g.associateCodeTo(new Game(new Player[1])));

    }
    */
    public boolean alreadyExistGameCode(int gameCode){  // metodo che controlla se esiste già un privateGAME con lo stesso gameCode
        if(associations.get(gameCode).equals(null) && !readyToUsePrivateCode.contains(gameCode)){   // perchè la prima è sempre falsa?
            readyToUsePrivateCode.add(gameCode);
            return false;
        }
        else
            return true;

    }


}
