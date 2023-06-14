package org.polimi.client;

import org.polimi.messages.Message;
import org.polimi.messages.MessageType;
import org.polimi.servernetwork.controller.InternalComunication;
import org.polimi.servernetwork.server.RMIinterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RMIClient extends Client implements RMICallback  {
    private static final int COUNTDOWN = 5;
    private static final int port = 1099;
    private ClientControllerInterface cliClientController;
    private RMIinterface server;
    private boolean connected;
    private int countDown;

    public RMIClient(int port) throws IOException, NotBoundException {
        super(port);
        createClientController();
        this.connected = false;
        this.countDown = COUNTDOWN;
        boolean bool;
        do {
            bool = this.startConnection();
        } while (!bool);
        this.login();
        //rmiClient.startChatClient();
    }


    public ClientControllerInterface getClientController() {
        return cliClientController;
    }

    private void createClientController() throws RemoteException {
        this.cliClientController = new CliClientController(this);
    }

    /**
     * search the server using RMI registry and if the server is found set the boolean connected to true
     * @return true if connection is established, false otherwise
     * @throws IOException
     * @throws NotBoundException
     */
    public boolean startConnection() throws IOException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(port);
        server = (RMIinterface) registry.lookup("server");
        if (server != null) {
            System.out.println("connesso");
            connected = true;
            return true;
        }
        return false;
    }

    /**
     * TODO: gestire la riconnessione, cosa succede se usernameAlreadyTaken è NEVER_USED ma quando chiamo login qualcuno
     *  me lo ha rubato?
     *
     *  calls login method of the server. if the return value is ALREADY_USED, the client is asked to choose another
     *  if the return value is OK the RMI client object is created and given to the server using subscribe method
     *  in this case is also started the decrementer thread
     * @throws RemoteException
     */
    public void login() throws RemoteException {
        //UsernameAndGameModeMessage message = new UsernameAndGameModeMessage(this.username, this.gamemode, -1);
        /*
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

         */
        InternalComunication internalComunication;
        do{
            chooseUsername();
            internalComunication = server.login(new Message(this.username, MessageType.USERNAME));
            if (internalComunication == InternalComunication.ALREADY_TAKEN_USERNAME) {
                cliClientController.alreadyTakenUsername();
            }
        }while(internalComunication == InternalComunication.ALREADY_TAKEN_USERNAME);
        if (internalComunication == InternalComunication.RECONNECTION) {
            cliClientController.reconnectionSuccessful();
            //serve altro codice?
        }
        if (internalComunication == InternalComunication.OK) {
            cliClientController.loginSuccessful();
            RMICallback clientStub = (RMICallback) UnicastRemoteObject.exportObject(this, 0);
            server.subscribe(username, clientStub);
            new Thread(new Decrementer(this)).start();
        }
    }

    @Override
    public void sendMessage(Message message) throws RemoteException {
        server.onMessage(message);
    }

    public void chooseUsername() {
        Message message = cliClientController.chooseUsername();
        this.username = message.getUsername();
    }

    public boolean ifConnected() {
        return connected;
    }

    public Message handleMessage(Message message) {
        return cliClientController.handleMessage(message);
    }

    public RMIinterface getServer() {
        return this.server;
    }

    public void disconnect() {
        cliClientController.disconnect();
    }
    @Override
    public String toString() {
        return username;
    }

    /**
     * Da chiamare solo se c'è un messaggio da leggere
     */
    @Override
    public void getNotified() throws RemoteException {
        Message message, messageFromServer=null;
        do{
            messageFromServer = server.getMessage(username);
            if(messageFromServer == null)
                System.out.println("cercando di leggere....");
        }while(messageFromServer==null);
        if(messageFromServer!=null){
            System.out.println("appena ricevuto questo dal server: "+ messageFromServer);
        }
        message = handleMessage(messageFromServer);
        if (message != null){
            System.out.println("inviando questo al server: "+ message +"\n in risposta a "+ messageFromServer);
            server.onMessage(message);
        }
    }
    @Override
    public void ping(){
        countDown = COUNTDOWN;
    }

    public void decrementCountDown() {
        countDown--;
        if(countDown == 0) {
            System.out.println("lost comunication with the server, ping timeout");
            disconnect();
        }
    }
    public void receiveChatMessage(String message) throws RemoteException{
        System.out.println(message);
    }

    public void startChatClient(){
        new Thread(()->{
            BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));
            String userInput;
            while (true) {
                try {
                    if (!((userInput = consoleInput.readLine()) != null)) break;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                try {
                    if(userInput.startsWith("$")){
                        String cleanedMessage = userInput.substring(1).trim();
                        server.sendChatMessage(username + ": "+ cleanedMessage);
                    }
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }
}




