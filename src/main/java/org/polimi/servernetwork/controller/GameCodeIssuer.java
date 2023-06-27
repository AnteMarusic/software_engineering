package org.polimi.servernetwork.controller;

import java.util.*;

public class GameCodeIssuer {
    private final Map<Integer, GameController> associations;
    private final List<Integer> freedIdCodes;
    private int currentHighestCode;

    public GameCodeIssuer () {
        this.associations = new HashMap<>();
        this.freedIdCodes = new LinkedList<>();
        this.currentHighestCode = 0;
    }
    public void createRow (int gameCode) {
        associations.put(gameCode, null);
    }
    public void setGameController (int gameCode, GameController gameController) {
        associations.remove(gameCode);
        associations.put(gameCode, gameController);
    }
    public void createRow (int gameCode, GameController gameController) {
        associations.put(gameCode, gameController);
        System.out.println("(GameCodeIssuer createRow) created row for " + gameCode);
    }

    public synchronized boolean containsIdCode (int idCode) {return associations.containsKey(idCode);}
    public synchronized GameController getGameController(int idCode) throws NoSuchElementException {
        if (!associations.containsKey(idCode))
            throw new NoSuchElementException();
        else {
            return associations.get(idCode);
        }
    }

    /**
     * Associates a new idCode to a gameController. Makes sure that the idCode is unique.
     * @param gameController the gameController to be associated
     * @return the idCode associated to the gameController
     * @throws NullPointerException if gameController is null
     */
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
                currentHighestCode ++;
                associations.put(currentHighestCode, gameController);
                return currentHighestCode;
            }
        }
    }
    public synchronized int reservCodeTo(){
        if (!freedIdCodes.isEmpty()) {
            int temp = freedIdCodes.get(0);
            associations.put(temp, null);
            return temp;
        }
        else {
            currentHighestCode ++;
            associations.put(currentHighestCode, null);
            return currentHighestCode;
        }
    }
    public synchronized void associatePrivateCodeTo (GameController gameController, int gameCode) throws NullPointerException{
        if (gameController == null)
            throw new NullPointerException();
        else {
            associations.put(gameCode, gameController);
        }
    }

    public synchronized boolean alreadyExistGameCode(int gameCode){  // metodo che controlla se esiste già un privateGAME con lo stesso gameCode
        if(associations.get(gameCode) == null){
            return false;
        }
        else
            return true;

    }
    public void removeGame(int gameCode){
        associations.remove(gameCode);
        freedIdCodes.add(gameCode);
    }


}
