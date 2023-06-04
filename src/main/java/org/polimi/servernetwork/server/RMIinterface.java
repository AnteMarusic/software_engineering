package org.polimi.servernetwork.server;

import org.polimi.client.RMICallback;
import org.polimi.messages.Message;
import org.polimi.messages.RMIAvailability;
import org.polimi.messages.UsernameStatus;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIinterface extends Remote {
    void login(Message usernameMessage) throws IOException, NotBoundException;
    void onMessage(Message message) throws RemoteException;
    void reconnection(Message message) throws RemoteException;
    Message getMessage(String username)throws RemoteException;
    /*RMIAvailability messagesAvailable(String username)throws RemoteException;*/
    UsernameStatus usernameAlreadyTaken(String username) throws RemoteException;
    void ping (String username) throws RemoteException;
    void subscribe(String username, RMICallback rmiclient) throws RemoteException;

}
