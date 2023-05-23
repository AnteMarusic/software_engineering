package org.polimi.servernetwork.server;

import org.polimi.messages.Message;
import org.polimi.messages.RMIAvailability;
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
    public void login(Message usernameandGameModeMessage) throws RemoteException{
        ClientHandler clienthandler = new ClientHandler(true, null, usernameIssuer, gameCodeIssuer, lobbyController);
        clienthandler.onMessage(usernameandGameModeMessage);
    }

//perchè ci sono due metodi con nomi uguali, uno qua e uno in client handler?
    public void onMessage(Message message) throws RemoteException{
        ClientHandler clientHandler = usernameIssuer.getClientHandler(message.getUsername());
        clientHandler.onMessage(message);
    }
    public void disconnect() throws RemoteException{

    }
    public Message getMessage(String username)throws RemoteException{
        ClientHandler clientHandler = usernameIssuer.getClientHandler(username);
        return clientHandler.popMessageRMI();
    }
    public RMIAvailability messagesAvailable(String username)throws RemoteException{
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
    public boolean usernameAlreadyTaken(String username) throws RemoteException{
        ClientHandler clientHandler = usernameIssuer.getClientHandler(username);
        if(clientHandler == null)
            return false;
        else
            return true;
    }

}

