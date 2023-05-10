package org.polimi.server.controller;

import org.polimi.messages.ErrorMessage;
import org.polimi.messages.ErrorType;
import org.polimi.messages.Message;
import org.polimi.server.ConnectionStatus;
import org.polimi.server.InternalComunication;

import javax.management.InstanceAlreadyExistsException;
import java.lang.reflect.Array;
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
    public synchronized InternalComunication handleMessage(String username){
        if(!map.containsKey(username)){
            Object[] object = new Object[2];
            object[0]= ConnectionStatus.CONNECTED;
            object[1]=null;
            map.put(username,object);
            return InternalComunication.OK;
        }
        else{
            Object[] object;
            object = map.get(username);
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
        Object[] object = map.get(username);
        object[1] = gameCode;                // non so se vada bene fatto così
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
