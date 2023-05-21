package org.polimi.client;

import org.polimi.messages.*;
import org.polimi.servernetwork.server.RMIinterface;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIClient extends Client{
    private int port;
    private ClientController clientController;
    private GameMode gamemode;
    private transient RMIinterface server;
    private boolean connected;

    RMIClient(int port) throws IOException, NotBoundException {
        super(port);
        createClientController();
        this.connected=false;
    }
    private void createClientController() throws RemoteException {
        this.clientController = new ClientController(this);
    }
    private boolean startConnection() throws IOException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(port);
        server = (RMIinterface) registry.lookup("server");
        if(server!=null){
            connected = true;
            return true;
        }
        else return false;
    }
    public void login() throws RemoteException {
        UsernameAndGameModeMessage message = new UsernameAndGameModeMessage(this.username, this.gamemode, -1);
        server.login(message);
    }
    public void sendMessage(Message message) throws RemoteException {
        if (server == null) {
            throw new RemoteException();
        }

        server.onMessage(message);
    }
    public void chooseUsername(){
        Message message = clientController.chooseUsername();
        this.username = message.getUsername();
    }
    public void chooseGameMode(){
        this.gamemode= ((ChosenGameModeMessage)clientController.chooseGameMode()).getGameMode();
    }
    public boolean ifConnected(){
        return connected;
    }
    public Message handleMessage(Message message){
         return clientController.handleMessage(message);
    }
    public RMIinterface getServer(){
        return this.server;
    }

    public static void main (String[] args) throws IOException, NotBoundException {
        boolean bool;
        Message message, messagefromserver;
        RMIClient rmiClient= new RMIClient(1099);
        do{
            bool=rmiClient.startConnection();
        }while(!bool);
        rmiClient.chooseUsername();//comunicazione solo client e clientcontroller
        rmiClient.chooseGameMode();//comunicazione solo client e clientcontroller
        rmiClient.login();
        while(rmiClient.ifConnected()){
            if(rmiClient.getServer().messagesAvailable(rmiClient.getUsername())){
                messagefromserver=rmiClient.getServer().getMessage(rmiClient.getUsername());
                System.out.println("questo dal server "+messagefromserver);
                message= rmiClient.handleMessage(messagefromserver);
                if(message!=null)
                    rmiClient.sendMessage(message);
            }
        }
    }

}
