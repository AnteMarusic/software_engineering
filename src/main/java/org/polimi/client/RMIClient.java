package org.polimi.client;

import org.polimi.messages.Message;
import org.polimi.messages.MessageType;
import org.polimi.messages.RMIAvailability;
import org.polimi.messages.UsernameStatus;
import org.polimi.servernetwork.server.RMIinterface;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.Queue;

public class RMIClient extends Client implements RMICallback  {
    private static final int port = 1099;
    private ClientController clientController;
    private RMIinterface server;
    private boolean connected;
    private Queue<Message> RMIMessages;

    private RMIAvailability availability;

    public RMIClient(int port) throws IOException, NotBoundException {
        super(port);
        createClientController();
        this.RMIMessages = new LinkedList<>();
        this.connected = false;
        this.availability=RMIAvailability.NOT_AVAILABLE;
    }

    public ClientController getClientController() {
        return clientController;
    }

    private void createPinger() {
        new Thread(new Pinger(this.username, this.server, this)).start();
        System.out.println("RMI client stampa: pinger creato");
    }

    private void createClientController() throws RemoteException {
        this.clientController = new ClientController(this);
    }

    public boolean startConnection() throws IOException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(port);
        server = (RMIinterface) registry.lookup("server");
        if (server != null) {
            System.out.println("connesso");
            connected = true;
            return true;
        } else return false;
    }

    /**
     * TODO: gestire la riconnessione, cosa succede se usernameAlreadyTaken è NEVER_USED ma quando chiamo login qualcuno
     *  me lo ha rubato?
     *
     * @throws RemoteException
     */
    public void login() throws RemoteException {
        //UsernameAndGameModeMessage message = new UsernameAndGameModeMessage(this.username, this.gamemode, -1);
        UsernameStatus usernameStatus = null;
        do {
            chooseUsername();//comunicazione solo client e client-controller
            usernameStatus = server.isUsernameAlreadyTaken(this.username);
            if (usernameStatus == UsernameStatus.USED) {
                System.out.println("Already taken username, choose another");
            }
        } while (usernameStatus == UsernameStatus.USED);
        Message usernameMessage = new Message(this.username, MessageType.USERNAME);
        if (usernameStatus == UsernameStatus.DISCONNECTED) {  // mi devo occupare della riconnessione
            server.reconnection(usernameMessage);
        }
        else if (usernameStatus == UsernameStatus.NEVER_USED) {
            try {
                RMICallback clientStub = (RMICallback) UnicastRemoteObject.exportObject(this, 0);
                server.subscribe(username, clientStub);
                server.login(usernameMessage);
                createPinger();
            } catch (IOException | NotBoundException e) {
                System.out.println("quiii");
                getClientController().disconnect();
            }
        }
    }

    public void sendMessage(Message message) throws RemoteException {
        if (server == null) {
            throw new RemoteException();
        }

        server.onMessage(message);
    }

    public void chooseUsername() {
        Message message = clientController.chooseUsername();
        this.username = message.getUsername();
    }

    /*public void chooseGameMode(){
        this.gamemode= ((ChosenGameModeMessage)clientController.chooseGameMode()).getGameMode();
    }

     */
    public boolean ifConnected() {
        return connected;
    }

    public Message handleMessage(Message message) {
        return clientController.handleMessage(message);
    }

    public RMIinterface getServer() {
        return this.server;
    }

    public void disconnect() {
        clientController.disconnect();
    }

    public Message popMessageRMI() {
        return RMIMessages.remove();
    }

    public boolean messagesToRead() {
        return RMIMessages.size() != 0;
    }
    public void addMessage(Message message){
        RMIMessages.add(message);
    }
    /*public static void main(String[] args) throws IOException, NotBoundException, InterruptedException {
        boolean bool;
        UsernameStatus alreadyTaken = null;
        Message message, messageFromServer = null;
        RMIClient rmiClient = new RMIClient(port);
        do {
            bool = rmiClient.startConnection();
        } while (!bool);
        rmiClient.login();
        while (rmiClient.ifConnected()) {
            /*RMIAvailability status = RMIAvailability.NOT_AVAILABLE;
            try{
                 status = rmiClient.getServer().messagesAvailable(rmiClient.getUsername());
            }
            catch ( RemoteException e){
                // gestire la disconnessione del server
            }
            //System.out.println(status);
            if(status == RMIAvailability.AVAILABLE){

              try{
                  messageFromServer=rmiClient.getServer().getMessage(rmiClient.getUsername());
              }
              catch ( RemoteException e){
                  // gestire la disconnessione del server
              }
            // messagefromserver potrebbe essere null
            if (rmiClient.messagesToRead()) {
                messageFromServer=rmiClient.popMessageRMI();
                System.out.println("questo dal server " + messageFromServer);
                message = rmiClient.handleMessage(messageFromServer);
                if (message != null)
                    rmiClient.sendMessage(message);
            }
        }
    }*/

    @Override
    public String toString() {
        return username;
    }

    @Override
    public synchronized void getNotified() throws RemoteException {
        Message message, messageFromServer;
        messageFromServer = server.getMessage(username);
        message = handleMessage(messageFromServer);
        if (message != null)
            sendMessage(message);
    }
    @Override
    public void ping(){
        
    }
}



