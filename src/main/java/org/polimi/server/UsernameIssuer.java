package org.polimi.server;

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

    public synchronized void addUsername (String username) throws InstanceAlreadyExistsException {
        if (username != null && !map.containsKey(username))
            map.put(username, null);
        else {
            throw new InstanceAlreadyExistsException();
        }
    }

    public synchronized void mapUsernameToGameCode(String username, int gameCode){
        Object [] value = new Object[2];
        value[0] = new int[1];
        value[0] = gameCode;
        value[1] = new boolean[1];
        value[1] = true;
        this.map.put(username, value);
    }

    public synchronized boolean containsUsername (String username) throws NullPointerException{
        if (username != null) {
            return map.containsKey(username);
        }
        else {
            throw new NullPointerException();
        }
    }
}
