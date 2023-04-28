package org.polimi.server;

import javax.management.InstanceAlreadyExistsException;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

public class UsernameIssuer {
    private final Set<String> usernameSet;

    public UsernameIssuer () {
        usernameSet = new HashSet<String>();
    }

    public synchronized void removeUsername (String username) throws NoSuchElementException {
        if (username != null && usernameSet.contains(username))
            usernameSet.remove(username);
        else {
            throw new NoSuchElementException();
        }
    }

    public synchronized void addUsername (String username) throws InstanceAlreadyExistsException {
        if (username != null && !usernameSet.contains(username))
            usernameSet.add(username);
        else {
            throw new InstanceAlreadyExistsException();
        }
    }

    public synchronized boolean containsUsername (String username) throws NullPointerException{
        if (username != null) {
            return usernameSet.contains(username);
        }
        else {
            throw new NullPointerException();
        }
    }
}
