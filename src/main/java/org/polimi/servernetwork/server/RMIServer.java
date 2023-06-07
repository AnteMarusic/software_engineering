package org.polimi.servernetwork.server;

import org.polimi.client.Pinger;
import org.polimi.client.RMICallback;
import org.polimi.messages.Message;
import org.polimi.messages.RMIAvailability;
import org.polimi.messages.UsernameStatus;
import org.polimi.servernetwork.controller.*;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class RMIServer extends UnicastRemoteObject implements RMIinterface {
    private GameCodeIssuer gameCodeIssuer;
    private UsernameIssuer usernameIssuer;
    private LobbyController lobbyController;
    private Map<String, RMICallback> subscribers;
    public RMIServer(GameCodeIssuer gameCodeIssuer, UsernameIssuer usernameIssuer, LobbyController lobbyController) throws RemoteException{
        this.gameCodeIssuer = gameCodeIssuer;
        this.usernameIssuer = usernameIssuer;
        this.lobbyController = lobbyController;
        this.subscribers = new HashMap<>();
    }
    /*

    /**
     * this method is called when a client wants to login and it is sure that the username provided is not already taken
     * @param usernameMessage the message containing the username
     * @throws RemoteException
     */

    @Override
    public void login(Message usernameMessage) throws IOException, NotBoundException {
        ClientHandler clienthandler = new ClientHandler(true, null, usernameIssuer, gameCodeIssuer, lobbyController);
        createPinger();
        //new Thread(clienthandler).start();
        clienthandler.onMessage(usernameMessage);
        subscribers.get(usernameMessage.getUsername()).getNotified();
    }
    private createPinger(){
            new Thread(new Pinger(this.username, this.server, this)).start();
            System.out.println("RMI client stampa: pinger creato");
    }


    @Override
    public void onMessage(Message message) throws RemoteException{
        ClientHandler clientHandler = usernameIssuer.getClientHandler(message.getUsername());
        clientHandler.onMessage(message);
        subscribers.get(message.getUsername()).getNotified();
    }
    public void reconnection(Message message) throws RemoteException {
        ClientHandler clientHandler = new ClientHandler(true, null, usernameIssuer, gameCodeIssuer, lobbyController);
        clientHandler.onMessage(message);
        subscribers.get(message.getUsername()).getNotified();
    }
    @Override
    public void ping (String username) throws RemoteException {
        System.out.println("sono in classe RMI server: ping di " + username);
        ClientHandler clientHandler = usernameIssuer.getClientHandler(username);
        clientHandler.resetCountDown();
    }
    public Message getMessage(String username)throws RemoteException{
        ClientHandler clientHandler = usernameIssuer.getClientHandler(username);
        return clientHandler.popMessageRMI();
    }
    /*@Override
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

    }*/
    @Override
    public UsernameStatus isUsernameAlreadyTaken(String username) throws RemoteException {
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
    public void subscribe(String username, RMICallback rmiclient) throws RemoteException{
        subscribers.put(username, rmiclient);
    }

}

