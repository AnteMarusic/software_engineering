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

    public static void main (String[] args) throws IOException, NotBoundException, InterruptedException {
        boolean bool;
        boolean alreadyTaken = false;
        Message message, messagefromserver;
        RMIClient rmiClient= new RMIClient(port);
        do{
            bool=rmiClient.startConnection();
        }while(!bool);
        do {
            rmiClient.chooseUsername();//comunicazione solo client e clientcontroller
            alreadyTaken = rmiClient.server.usernameAlreadyTaken(rmiClient.username);
            if(alreadyTaken){
                System.out.println("Already taken username, choose another");
            }
            // va gestito il caso in cui si tratti di una riconnessione
            // devo quindi aggiungere un metodo che controlli lo stato dell'user (già presente)
            // nel caso in cui alreadytaken sia true
            // siccome se in UsernameIssuer c'è già quel nome ma il suo stato è disconnesso
            // in realtà accetto quello username e semplicemente faccio la reconnection
        }while(alreadyTaken);
        rmiClient.chooseGameMode();
        rmiClient.login();
        /*
            dato che prima chiediamo il nome poi il gamecode e solo dopo inviamo il messaggio se
            dopo aver messo il nome ma prima di aver messo il gameMode qualcuno con Socket sceglie
            lo stesso username il server non sapeva che il client con RMI aveva scelto anche lui quell'user
            siccome il client non gliel'ha ancora comunicato con un messaggio
            Si va così in un errore
         */
        while(rmiClient.ifConnected()){
            Thread.sleep(1000);
            RMIAvailability status = rmiClient.getServer().messagesAvailable(rmiClient.getUsername());
            System.out.println(status);
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
