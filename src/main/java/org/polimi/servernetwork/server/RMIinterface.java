package org.polimi.servernetwork.server;

import org.polimi.client.RMICallback;
import org.polimi.messages.Message;
import org.polimi.messages.UsernameStatus;
import org.polimi.servernetwork.controller.InternalComunication;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIinterface extends Remote {
    InternalComunication login(Message usernameMessage) throws RemoteException;
    void onMessage(Message message) throws RemoteException;
    void reconnection(String username, RMICallback rmiclient) throws RemoteException;
    Message getMessage(String username)throws RemoteException;
    /*RMIAvailability messagesAvailable(String username)throws RemoteException;*/
    //UsernameStatus isUsernameAlreadyTaken(String username) throws RemoteException;
    void subscribe(String username, RMICallback rmiClient) throws RemoteException;
    void sendChatMessage(String message) throws RemoteException;
}
