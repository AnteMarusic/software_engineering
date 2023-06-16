package org.polimi.servernetwork.controller;

import org.polimi.client.view.Cli;

import java.util.*;

/**
 * this class uses a map where the key is the username and value is an array of 3 objects
 * index 0 connection status of the player
 * index 1 the gameCode
 * index 2 the clientHandler
 */
public class UsernameIssuer {
    private final Map<String, Object[]> map;

    /**
     * constructor, initializes the map
     */
    public UsernameIssuer () {
        map = new HashMap<String, Object[]>();
    }

    /**
     * associates a client handler to a username. in case the username is not registered it throws an exception
     * @param clientHandler the client handler to be associated
     * @param username the username of the client handler
     */
    public synchronized void setClientHandler(ClientHandler clientHandler, String username){
        Object[] object = map.get(username);
        if(object == null){
            throw new RuntimeException("you tried to set a clientHandler to a non registered username");
        }
        object[2] = clientHandler;
        System.out.println("(UsernameIssuer) added ClientHandler to: " + username);
        map.put(username, object);
        printMap();
    }

    /**
     * sinchronized even if it is called only by the decrementer
     * @return a list of all the active clientHandlers
     */
    public synchronized List <ClientHandler> getActiveClientHandlers () {
        List <ClientHandler> list = new ArrayList<>();
        for (String s : map.keySet()) {
            if ((map.get(s))[0]== ConnectionStatus.CONNECTED) {
                list.add((ClientHandler)map.get(s)[2]);
            }
        }
        return list;
    }

    public synchronized void removeUsername (String username) throws NoSuchElementException {
        if (username != null && map.containsKey(username)) {
            map.remove(username);
            System.out.println("(UsernameIssuer) removed " + username);
        }
        else {
            throw new NoSuchElementException();
        }
    }

    private synchronized void printMap () {
        System.out.println("(UsernameIssuer) print table");
        Object [] o;
        for (String s : map.keySet()) {
            System.out.print("(UsernameIssuer) "+s);
            o = map.get(s);
            System.out.println(" : connection status: " + o[0] + ", game code: " + o [1] + ", client handler: " + o[2]);
        }
    }

    /**
     * if username is not in the data structure the method stores a line for the new player
     * with connection status CONNECTED and no other attributes and responds OK.
     * if the username is present, either is currently connected or is disconnected. In the first case the method
     * returns ALREADY_TAKEN_USERNAME, in the second case RECONNECTION.
     * @param username username of the player that is connecting
     * @return return an InternalComunication that explains what to do
     */
    public synchronized InternalComunication login(String username){
        printMap();
        if(!map.containsKey(username)){
            Object[] object = new Object[3];
            object[0]= ConnectionStatus.CONNECTED;
            object[1]=null;
            object[2] = null;
            map.put(username,object);
            System.out.println("(UsernameIssuer) reserved row for: " + username);
            printMap();
            return InternalComunication.OK;
        }
        else{
            Object[] object;
            object = map.get(username);
            System.out.println("(UsernameIssuer) impossible to reserve a row for: " + username);
            printMap();
            if(object[0]==ConnectionStatus.CONNECTED){
                return InternalComunication.ALREADY_TAKEN_USERNAME;
            }
            else{
                return InternalComunication.RECONNECTION;
            }
        }
    }

    public synchronized ClientHandler getClientHandler(String username){
        Object[] object = map.get(username);
        if(object == null){
            return null;
        }
        else{
            if(object[2]==null){
                throw new RuntimeException("for some reason the clientHandler associated to the username provided is null");
            }
            return (ClientHandler)object[2];
        }
    }

    public synchronized int getGameID (String username){
        Object [] object;
        object = map.get(username);
        return (int)object[1];
    }


    public synchronized void mapUsernameToGameCode(String username, int gameCode){
        // assegno nella mappa a ogni username il suo gamecode
        Integer code = gameCode;
        Object[] object = map.get(username);
        object[1] = code;                // non so se vada bene fatto così o bisogna fare il boxing
        map.put(username, object);
    }

    public synchronized boolean containsUsername (String username) throws NullPointerException{
        if (username != null) {
            return map.containsKey(username);
        }
        else {
            throw new NullPointerException();
        }
    }
    public synchronized void setConnect(String username){
        Object[] object = map.get(username);
        object[0]=ConnectionStatus.CONNECTED;
        map.put(username, object);
    }
    public synchronized ConnectionStatus getConnectionStatus(String username){
        Object [] object;
        object = map.get(username);
        return (ConnectionStatus)object[0];
    }

    public synchronized void setDisconnect(String username){
        Object[] object = map.get(username);
        object[0]=ConnectionStatus.DISCONNECTED;
        map.put(username, object);
    }
}

