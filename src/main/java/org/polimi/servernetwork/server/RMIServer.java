package org.polimi.servernetwork.server;

import org.polimi.messages.Message;
import org.polimi.messages.RMIAvailability;
import org.polimi.messages.UsernameStatus;
import org.polimi.servernetwork.controller.*;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RMIServer extends UnicastRemoteObject implements RMIinterface {
    private GameCodeIssuer gameCodeIssuer;
    private UsernameIssuer usernameIssuer;
    private LobbyController lobbyController;
    public RMIServer(GameCodeIssuer gameCodeIssuer, UsernameIssuer usernameIssuer, LobbyController lobbyController) throws RemoteException{
        this.gameCodeIssuer = gameCodeIssuer;
        this.usernameIssuer = usernameIssuer;
        this.lobbyController = lobbyController;
    }
    /*
    public void login(Message usernameandGameModeMessage) throws RemoteException{
        ClientHandler clienthandler = new ClientHandler(true, null, usernameIssuer, gameCodeIssuer, lobbyController);
        clienthandler.onMessage(usernameandGameModeMessage);
    }

     */
    public void login(Message usernameMessage) throws RemoteException{
        ClientHandler clienthandler = new ClientHandler(true, null, usernameIssuer, gameCodeIssuer, lobbyController);
        clienthandler.onMessage(usernameMessage);
    }

//perchè ci sono due metodi con nomi uguali, uno qua e uno in client handler?
    public void onMessage(Message message) throws RemoteException{
        ClientHandler clientHandler = usernameIssuer.getClientHandler(message.getUsername());
        clientHandler.onMessage(message);
    }
    public void reconnection() throws RemoteException{

    }
    public void disconnect() throws RemoteException{

    }
    @Override
    public void ping (String username) throws RemoteException {
        ClientHandler clientHandler = usernameIssuer.getClientHandler(username);
        clientHandler.resetCountDown();
    }
    public Message getMessage(String username)throws RemoteException{
        ClientHandler clientHandler = usernameIssuer.getClientHandler(username);
        return clientHandler.popMessageRMI();
    }
    public RMIAvailability messagesAvailable(String username)throws RemoteException{
        countDown(username);
        ClientHandler clientHandler = usernameIssuer.getClientHandler(username);
        if(clientHandler == null){
            throw new RuntimeException("something wrong happened, in method messageAvabile of RMI server because it returned a null client handler");
        }
        else{
            if(!clientHandler.rmiMessagesEmpty()){
                return  RMIAvailability.AVAILABLE;
            }
            else{
                return RMIAvailability.NOT_AVAILABLE;
            }
        }

    }
    public UsernameStatus usernameAlreadyTaken(String username) throws RemoteException {
        ClientHandler clientHandler = usernameIssuer.getClientHandler(username);
        if (clientHandler == null)
            return UsernameStatus.NEVER_USED;
        else {
            if (usernameIssuer.getConnectionStatus(clientHandler.getUsername()) == ConnectionStatus.CONNECTED)
                return UsernameStatus.USED;
            else if (usernameIssuer.getConnectionStatus(clientHandler.getUsername()) == ConnectionStatus.DISCONNECTED) {
                return UsernameStatus.DISCONNECTED;
            }
            else
                throw new RuntimeException("problemi in usernameAlreadyTaken");

        }
    }

    private void countDown(String username){
        ClientHandler clientHandler = usernameIssuer.getClientHandler(username);
        clientHandler.countDown();
    }



}

