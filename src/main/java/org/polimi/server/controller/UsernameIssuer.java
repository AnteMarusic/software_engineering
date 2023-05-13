package org.polimi.server.controller;

import java.util.*;

public class UsernameIssuer {
    private final Map<String, Object[]> map;

    public UsernameIssuer () {
        map = new HashMap<String, Object[]>();
    }

    public synchronized void removeUsername (String username) throws NoSuchElementException {
        if (username != null && map.containsKey(username))
            map.remove(username);
        else {
            throw new NoSuchElementException();
        }
    }

    private void printMap () {
        Object [] o;
        for (String s : map.keySet()) {
            System.out.print(s);
            o = map.get(s);
            System.out.println(" : " + o[0] + ", " + o [1]);
        }
    }

    /**
     *
     * @param username username of the player that is connecting
     * @return return an InternalComunication that explains what to do
     */
    public synchronized InternalComunication handleMessage(String username){
        System.out.println("handle message before");
        printMap();
        if(!map.containsKey(username)){
            Object[] object = new Object[2];
            object[0]= ConnectionStatus.CONNECTED;
            object[1]=null;
            map.put(username,object);
            System.out.println("handle message if");
            printMap();
            return InternalComunication.OK;
        }
        else{
            Object[] object;
            object = map.get(username);
            System.out.println("handle message else");
            printMap();
            if(object[0]==ConnectionStatus.CONNECTED){
                return InternalComunication.ALREADY_TAKEN_USERNAME;
            }
            else{
                return InternalComunication.RECONNECTION;
            }
        }
    }

    public int getGameID (String username){
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
    public void setConnect(String username){
        Object[] object = map.get(username);
        object[0]=ConnectionStatus.CONNECTED;
        map.put(username, object);
    }
}
