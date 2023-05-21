package org.polimi.servernetwork.server;

import org.polimi.messages.Message;
import org.polimi.servernetwork.controller.ClientHandler;
import org.polimi.servernetwork.controller.GameController;
import org.polimi.servernetwork.controller.LobbyController;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RMIImplementation extends UnicastRemoteObject implements RMIinterface {
    private Server server;
    public void login(Message usernameandGameModeMessage) throws RemoteException{
        ClientHandler clienthandler = new ClientHandler(true, null, server.getUsernameIssuer(), server.getGameCodeIssuer(), server.getLobby());
        clienthandler.onMessage(usernameandGameModeMessage);
    }

    RMIImplementation(Server server) throws RemoteException{
        this.server=server;
    }
    public void onMessage(Message message) throws RemoteException{
        ClientHandler clientHandler = getCorrectClientHandler(message.getUsername());
        clientHandler.onMessage(message);
    }
    public void disconnect() throws RemoteException{

    }
    private ClientHandler getCorrectClientHandler(String username)throws RemoteException{
        int gameId;
        GameController gamegc;
        LobbyController lobby= server.getLobby();
        ClientHandler clientHandler=lobby.getClienthandlerfromLobby(username);
        if(clientHandler == null){
            gameId=server.getUsernameIssuer().getGameID(username);
            gamegc=server.getGameCodeIssuer().getGameController(gameId);
            clientHandler = gamegc.getClienthandlerfromGC(username);
        }
        return clientHandler;
    }

    public Message getMessage(String username)throws RemoteException{
        ClientHandler clientHandler = getCorrectClientHandler(username);
        return clientHandler.popMessageRMI();
    }
    public boolean messagesAvailable(String username)throws RemoteException{
        ClientHandler clientHandler = getCorrectClientHandler(username);
        return !clientHandler.rmiMessagesEmpty();
    }

}
