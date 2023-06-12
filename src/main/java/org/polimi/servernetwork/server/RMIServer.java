package org.polimi.servernetwork.server;

import org.polimi.client.RMICallback;
import org.polimi.messages.Message;
import org.polimi.servernetwork.controller.*;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

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
    /**
     * this method reserves a line in the table of usernames in username issuer if the username provided as argument is not already taken
     * differently, it returns the status of the username
     * @param usernameMessage message containing the username
     * @return OK if the username is not already taken, RECONNECTION if you are reconnecting, ALREADY_TAKEN_USERNAME otherwise
     * @throws IOException
     * @throws NotBoundException
     */
    @Override
    public InternalComunication login(Message usernameMessage) throws RemoteException{
        InternalComunication internalComunication = this.usernameIssuer.login(usernameMessage.getUsername());
        if (internalComunication == InternalComunication.ALREADY_TAKEN_USERNAME) {
            return InternalComunication.ALREADY_TAKEN_USERNAME;
        } else if (internalComunication == InternalComunication.OK) {
            return InternalComunication.OK;
        } else if (internalComunication == InternalComunication.RECONNECTION){
            //InternalComunication.RECONNECTION
            return InternalComunication.RECONNECTION;
        }
        return internalComunication;
    }
    private void createPinger(String username, RMICallback rmiClient){
            new Thread(new Pinger(rmiClient, this, username)).start();
    }

    /**
     * TODO: implement this method
     * method invoked by Pinger when the client isn't reachable anymore
     * @param username of the client to disconnect
     */
    public void disconnect (String username) {

    }

    @Override
    public void onMessage(Message message) throws RemoteException{
        ClientHandler clientHandler = usernameIssuer.getClientHandler(message.getUsername());
        clientHandler.onMessage(message);
    }
    public void reconnection(Message message) throws RemoteException {
        /*ClientHandler clientHandler = new ClientHandler(true, null, usernameIssuer, gameCodeIssuer, lobbyController);*/
        //clientHandler.onMessage(message);
    }
    public Message getMessage(String username)throws RemoteException{
        RMIClientHandler clientHandler = (RMIClientHandler) usernameIssuer.getClientHandler(username);
        return clientHandler.popMessageRMI();
    }
    /**
     * this method inserts a rmiCallBack in the subscribers map, creates a clienthandler for that client, send the username
     * message to that clienthandler and creates the pinger.
     * @param username message containing the username, rmiclient containing the remote object linked to the rmiclient
     * @return OK if the username is not already taken, RECONNECTION if you are reconnecting, ALREADY_TAKEN_USERNAME otherwise
     * @throws RemoteException
     */
    public void subscribe(String username, RMICallback rmiclient) throws RemoteException{
        subscribers.put(username, rmiclient);
        Message usernameMessage = new Message(username);
        ClientHandler clientHandler = new RMIClientHandler(rmiclient, usernameIssuer, gameCodeIssuer, lobbyController);
        this.usernameIssuer.setClientHandler(clientHandler, usernameMessage.getUsername());
        onMessage(usernameMessage);
        createPinger(username, rmiclient);
    }
    public void sendChatMessage(String message) throws RemoteException{
        for(Map.Entry<String, RMICallback> entry : subscribers.entrySet()){
            entry.getValue().receiveChatMessage(message);
        }
    }


}

