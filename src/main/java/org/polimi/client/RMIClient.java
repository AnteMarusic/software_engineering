package org.polimi.client;

import org.polimi.client.view.gui.sceneControllers.SceneController;
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
import java.util.LinkedList;
import java.util.Queue;

/**
 * messages has to be sent in the right order, because they will be executed by the client
 * in the same order they have been sent (the same thing happen in SocketClient)
 */
public class RMIClient extends Client implements RMICallback  {
    private static final int COUNTDOWN = 5;
    private static final int port = 1099;
    private ClientControllerInterface clientController;
    private RMIinterface server;
    private boolean connected;
    private int countDown;
    private Queue<Message> messageQueue;
    private final Object taskLock;
    private final Object messageQueueLock;

    private final boolean guiMode;
    public RMIClient(int port, boolean guiMode) throws IOException, NotBoundException {
        super(port);
        this.guiMode = guiMode;
        if(guiMode){
            createGuiClientController();
        }else{
            createCliClientController();
        }
        this.connected = false;
        this.countDown = COUNTDOWN;
        //rmiClient.startChatClient();
        messageQueue = new LinkedList<Message>();
        taskLock = new Object();
        messageQueueLock = new Object();
    }


    public ClientControllerInterface getClientController() {
        return clientController;
    }

    private void createCliClientController() throws RemoteException {
        this.clientController = new CliClientController(this);
    }

    private void createGuiClientController() throws RemoteException {
        this.clientController = new GuiClientController(this);
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

    public boolean loginGui(String username) throws RemoteException {
        InternalComunication internalComunication;
        do{
            this.username = username;
            internalComunication = server.login(new Message(this.username, MessageType.USERNAME));
            if (internalComunication == InternalComunication.ALREADY_TAKEN_USERNAME) {
                return false;
            }
        }while(internalComunication == InternalComunication.ALREADY_TAKEN_USERNAME);
        if (internalComunication == InternalComunication.RECONNECTION) {
            return false;
        }
        if (internalComunication == InternalComunication.OK) {
            RMICallback clientStub = (RMICallback) UnicastRemoteObject.exportObject(this, 0);
            server.subscribe(this.username, clientStub);
            new Thread(new Decrementer(this)).start();
            return true;
        }
        return false;
    }
    public void login() throws RemoteException {
        InternalComunication internalComunication;
        do{
            chooseUsername();
            internalComunication = server.login(new Message(this.username, MessageType.USERNAME));
            if (internalComunication == InternalComunication.ALREADY_TAKEN_USERNAME) {
                clientController.alreadyTakenUsername();
            }
        }while(internalComunication == InternalComunication.ALREADY_TAKEN_USERNAME);
        if (internalComunication == InternalComunication.RECONNECTION) {
            clientController.reconnectionSuccessful();
            RMICallback clientStub = (RMICallback) UnicastRemoteObject.exportObject(this, 0);
            server.reconnection(username, clientStub);
        }
        if (internalComunication == InternalComunication.OK) {
            clientController.loginSuccessful();
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
        Message message = clientController.chooseUsername();
        this.username = message.getUsername();
    }

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
    @Override
    public String toString() {
        return username;
    }

    /**
     * this method is called by the server when a message is sent to the client
     * it adds the message to the message queue and starts a new thread that waits on taskLock to be free
     * and then calls handleMessage method of the clientController and in case of a message to be sent to the server
     * it sends this message.
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
        //message = handleMessage(messageFromServer);
        synchronized (messageQueueLock) {
            messageQueue.add(messageFromServer);
        }
        new Thread (()->{
            Message answer;
            Message message1;
            synchronized (taskLock) {
                synchronized (messageQueueLock) {
                    message1 = messageQueue.poll();
                }
                answer = handleMessage(message1);
            }
            if (answer != null){
                System.out.println("inviando questo al server: "+ answer +"\n in risposta a "+ message1);
                try {
                    server.onMessage(answer);
                } catch (RemoteException e) {
                    System.out.println("(RMIclient) error sending message to server");
                }
            }
        }).start();
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




