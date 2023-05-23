package org.polimi.servernetwork.server;

import org.polimi.messages.Message;
import org.polimi.messages.RMIAvailability;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIinterface extends Remote {
    void login(Message usernameMessage) throws RemoteException;
    void onMessage(Message message) throws RemoteException;
    void disconnect() throws RemoteException;
    Message getMessage(String username)throws RemoteException;
    RMIAvailability messagesAvailable(String username)throws RemoteException;
    boolean usernameAlreadyTaken(String username) throws RemoteException;
}
