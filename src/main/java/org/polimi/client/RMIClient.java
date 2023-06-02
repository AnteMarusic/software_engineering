package org.polimi.client;

import org.polimi.messages.*;
import org.polimi.servernetwork.server.RMIinterface;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIClient extends Client{
    private static final int port = 1099;
    private ClientController clientController;
    private transient RMIinterface server;
    private boolean connected;

    RMIClient(int port) throws IOException, NotBoundException {
        super(port);
        createClientController();
        this.connected=false;
    }

    public ClientController getClientController() {
        return clientController;
    }

    private void createPinger () {
        new Thread(new Pinger(this.username, this.server, this)).start();
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
        //UsernameAndGameModeMessage message = new UsernameAndGameModeMessage(this.username, this.gamemode, -1);
        Message message = new Message(this.username, MessageType.USERNAME);
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
    /*public void chooseGameMode(){
        this.gamemode= ((ChosenGameModeMessage)clientController.chooseGameMode()).getGameMode();
    }

     */
    public boolean ifConnected(){
        return connected;
    }
    public Message handleMessage(Message message){
         return clientController.handleMessage(message);
    }
    public RMIinterface getServer(){
        return this.server;
    }

    public void disconnect () {
        clientController.disconnect();
    }

    public static void main (String[] args) throws IOException, NotBoundException, InterruptedException {
        boolean bool;
        UsernameStatus alreadyTaken=null;
        Message message, messagefromserver;
        RMIClient rmiClient= new RMIClient(port);
        do{
            bool=rmiClient.startConnection();
        }while(!bool);
        do {
            rmiClient.chooseUsername();//comunicazione solo client e clientcontroller
            alreadyTaken = rmiClient.server.usernameAlreadyTaken(rmiClient.username);
            if (alreadyTaken == UsernameStatus.USED) {
                System.out.println("Already taken username, choose another");
            }
        }while(alreadyTaken == UsernameStatus.USED);
        if(alreadyTaken == UsernameStatus.DISCONNECTED){  // mi devo occupare della riconnessione
            rmiClient.server.reconnection();
        }
        else if(alreadyTaken == UsernameStatus.NEVER_USED){
            try {
                rmiClient.login();
                rmiClient.createPinger();
            } catch (RemoteException e) {
                rmiClient.getClientController().disconnect();
            }
        }
        while(rmiClient.ifConnected()){
            Thread.sleep(1000);
            RMIAvailability status = rmiClient.getServer().messagesAvailable(rmiClient.getUsername());
            //System.out.println(status);
            if(status == RMIAvailability.AVAILABLE){
                messagefromserver=rmiClient.getServer().getMessage(rmiClient.getUsername());
                System.out.println("questo dal server "+messagefromserver);
                message= rmiClient.handleMessage(messagefromserver);
                if(message!=null)
                    rmiClient.sendMessage(message);
            }
        }
    }

}
