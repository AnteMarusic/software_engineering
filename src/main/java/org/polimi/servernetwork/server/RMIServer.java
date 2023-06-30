package org.polimi.servernetwork.server;

import org.polimi.client.RMICallback;
import org.polimi.messages.Message;
import org.polimi.messages.MessageType;
import org.polimi.servernetwork.controller.*;

import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

public class RMIServer extends UnicastRemoteObject implements RMIinterface {
    private GameCodeIssuer gameCodeIssuer;
    private UsernameIssuer usernameIssuer;
    private LobbyController lobbyController;
    private Map<String, RMICallback> subscribers;

    private Registry registry;
    public RMIServer(int rmiPort, GameCodeIssuer gameCodeIssuer, UsernameIssuer usernameIssuer, LobbyController lobbyController) throws RemoteException{
        this.gameCodeIssuer = gameCodeIssuer;
        this.usernameIssuer = usernameIssuer;
        this.lobbyController = lobbyController;
        this.subscribers = new HashMap<>();

        try {
            System.setProperty("java.rmi.server.hostname", "192.168.244.63");
            this.registry = LocateRegistry.createRegistry(rmiPort);
            //Decrementer decrementer = new Decrementer(usernameIssuer);
            registry.bind("server", this);
            //new Thread(decrementer).start();
            System.out.println("RMI server is up");
        } catch (IOException | AlreadyBoundException e) {
            System.out.println("errore rmi server, l'errore è:" + e);
        }
    }
    /**
     * this method reserves a line in the table of usernames in username issuer if the username provided as argument is not already taken
     * differently, it returns the status of the username
     * @param usernameMessage message containing the username
     * @return OK if the username is not already taken, RECONNECTION if you are reconnecting, ALREADY_TAKEN_USERNAME otherwise
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
    private void createPinger(String username, RMIClientHandler clientHandler, RMICallback rmiClient){
        Pinger p = new Pinger(rmiClient, this, username);
        clientHandler.setPinger(p);
        new Thread(p).start();
    }

    /**
     * TODO: implement this method
     * method invoked by Pinger when the client isn't reachable anymore
     * @param username of the client to disconnect
     */
    public void disconnect (String username) {
        subscribers.remove(username);
        if(usernameIssuer.getClientHandler(username)!=null){
            usernameIssuer.getClientHandler(username).disconnect();
        }
    }

    @Override
    public void onMessage(Message message) throws RemoteException{
        ClientHandler clientHandler = usernameIssuer.getClientHandler(message.getUsername());
        if(clientHandler != null)
            clientHandler.onMessage(message);
    }

    public Message getMessage(String username)throws RemoteException{
        System.out.println("funziona");
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
        System.out.println("1");
        subscribers.put(username, rmiclient);
        System.out.println("1");
        //Message usernameMessage = new Message(username);
        ClientHandler clientHandler = new RMIClientHandler(rmiclient, usernameIssuer, gameCodeIssuer, lobbyController);
        clientHandler.setUsername(username);
        this.usernameIssuer.setClientHandler(clientHandler, username);
        //onMessage(usernameMessage);
        clientHandler.sendMessage (new Message(username, MessageType.CHOOSE_GAME_MODE ));
        createPinger(username, (RMIClientHandler) clientHandler , rmiclient);
    }

    public void subscribe2(String username) throws NotBoundException, RemoteException {
        System.out.println("1");
        RMICallback rmiclient = (RMICallback)registry.lookup(username);
        subscribers.put(username, rmiclient);
        System.out.println("1");
        //Message usernameMessage = new Message(username);
        ClientHandler clientHandler = new RMIClientHandler(rmiclient, usernameIssuer, gameCodeIssuer, lobbyController);
        clientHandler.setUsername(username);
        this.usernameIssuer.setClientHandler(clientHandler, username);
        //onMessage(usernameMessage);
        clientHandler.sendMessage (new Message(username, MessageType.CHOOSE_GAME_MODE ));
        createPinger(username, (RMIClientHandler) clientHandler, rmiclient);
    }

    public void reconnection(String username, RMICallback rmiclient) throws RemoteException {
        subscribers.put(username, rmiclient);
        ClientHandler clientHandler = new RMIClientHandler(rmiclient, usernameIssuer, gameCodeIssuer, lobbyController);
        clientHandler.setUsername(username);
        createPinger(username, (RMIClientHandler) clientHandler, rmiclient);
        clientHandler.reconnection();
    }
    public void sendChatMessage(String message) throws RemoteException{
        for(Map.Entry<String, RMICallback> entry : subscribers.entrySet()){
            entry.getValue().receiveChatMessage(message);
        }
    }


}

