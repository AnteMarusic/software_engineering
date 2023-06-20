package org.polimi.servernetwork.controller;

import org.polimi.messages.Message;
import org.polimi.messages.MessageType;

import java.util.*;
import java.util.stream.Stream;

public class LobbyController {
    //to synchronize and add update message
    private final ArrayList<ClientHandler> publicListOf2;
    private final ArrayList<ClientHandler> publicListOf3;
    private final ArrayList<ClientHandler> publicListOf4;
    private final Map<Integer, ArrayList<ClientHandler>> privateGameOf2;
    private final Map<Integer, ArrayList<ClientHandler>> privateGameOf3;
    private final Map<Integer, ArrayList<ClientHandler>> privateGameOf4;
    GameCodeIssuer gameCodeIssuer;
    UsernameIssuer usernameIssuer;

    public LobbyController(GameCodeIssuer gameCodeIssuer, UsernameIssuer usernameIssuer){
        this.gameCodeIssuer = gameCodeIssuer;
        this.usernameIssuer = usernameIssuer;
        this.publicListOf2 = new ArrayList<>(2);
        this.publicListOf3 = new ArrayList<>(3);
        this.publicListOf4 = new ArrayList<>(4);
        this.privateGameOf2 = new HashMap<Integer, ArrayList<ClientHandler>>();
        this.privateGameOf3 = new HashMap<Integer, ArrayList<ClientHandler>>();
        this.privateGameOf4 = new HashMap<Integer, ArrayList<ClientHandler>>();
    }

    private void printLobby (int gameMode) {
        switch (gameMode) {
            case 2 -> {
                synchronized (publicListOf2){
                    System.out.print("(LobbyController) lobby of two: ");
                    System.out.print("size: " + publicListOf2.size() + " contains: ");
                    for (ClientHandler c : publicListOf2) {
                        System.out.print(c.getUsername());
                    }
                    System.out.println();
                }
             }
            case 3 -> {
                synchronized (publicListOf3){
                    System.out.print("(LobbyController) lobby of three: ");
                    System.out.print("size: " + publicListOf3.size() + " contains: ");
                    for (ClientHandler c : publicListOf3) {
                        System.out.print(c.getUsername());
                    }
                    System.out.println();
                }
            }
            case 4 -> {
                synchronized (publicListOf4){
                    System.out.print("(LobbyController) lobby of four: ");
                    System.out.print("size: " + publicListOf4.size() + " contains: ");
                    for (ClientHandler c : publicListOf4) {
                        System.out.print(c.getUsername());
                    }
                    System.out.println();
                }
            }
        }
    }
    public void insertPlayer(ClientHandler clientHandler, int gameMode){
        switch(gameMode){
            case 2-> {
                synchronized (publicListOf2) {
                    publicListOf2.add(clientHandler);
                    System.out.println("(LobbyController) inserisco in lobby da due: " + clientHandler.getUsername());
                    printLobby(gameMode);
                    if(publicListOf2.size()==2){
                        this.createGame(2);
                    }
                    else {
                        clientHandler.sendMessage(new Message(clientHandler.getUsername(), MessageType.WAITING_IN_LOBBY));
                    }
                }
            }
            case 3-> {
                synchronized (publicListOf3) {
                    publicListOf3.add(clientHandler);
                    System.out.println("(LobbyController) inserisco in lobby da tre: " + clientHandler.getUsername());
                    printLobby(gameMode);
                    if(publicListOf3.size()==3){
                        this.createGame(3);
                    }
                    else{
                        clientHandler.sendMessage(new Message(clientHandler.getUsername(), MessageType.WAITING_IN_LOBBY));
                    }
                }
            }
            case 4-> {
                synchronized (publicListOf4) {
                    publicListOf4.add(clientHandler);
                    System.out.println("(LobbyController) inserisco in lobby da quattro: " + clientHandler.getUsername());
                    printLobby(gameMode);
                    if(publicListOf4.size()==4){
                        this.createGame(4);
                    }
                    else{
                        clientHandler.sendMessage(new Message(clientHandler.getUsername(), MessageType.WAITING_IN_LOBBY));
                    }
                }
            }
        }
    }

    private void createGame(int gameMode)  {
        switch (gameMode) {
            case 2-> {
                GameController gameController = new GameController(publicListOf2, usernameIssuer, gameCodeIssuer);
                int code = gameCodeIssuer.associateCodeTo(gameController);
                gameController.setGameCode(code);
                publicListOf2.forEach((clientHandler -> this.usernameIssuer.mapUsernameToGameCode(clientHandler.getUsername(), code)));
                publicListOf2.forEach(clientHandler -> clientHandler.setGameController(gameController));
                for(ClientHandler clientHandler: publicListOf2){
                    if(clientHandler.gameControllerPresent()){
                        System.out.println("(LobbyController) " + clientHandler.getUsername() + " ha il gameController");
                    }
                }
                gameController.initGameEnv();
                gameController.startGameTurn();
                publicListOf2.clear();
            }
            case 3-> {
                GameController gameController = new GameController(publicListOf3, usernameIssuer, gameCodeIssuer);
                gameController.initGameEnv();
                gameController.startGameTurn();
                int code = gameCodeIssuer.associateCodeTo(gameController);
                publicListOf3.forEach((clientHandler -> this.usernameIssuer.mapUsernameToGameCode(clientHandler.getUsername(), code)));
                publicListOf3.forEach(clientHandler -> clientHandler.setGameController(gameController));
                for(ClientHandler clientHandler: publicListOf3){
                    if(clientHandler.gameControllerPresent()){
                        System.out.println("(LobbyController) " + clientHandler.getUsername() + " ha il gameController");
                    }
                }
                publicListOf3.clear();
            }
            case 4-> {
                GameController gameController = new GameController(publicListOf4, usernameIssuer, gameCodeIssuer);
                gameController.initGameEnv();
                gameController.startGameTurn();
                int code = gameCodeIssuer.associateCodeTo(gameController);
                publicListOf4.forEach((clientHandler -> this.usernameIssuer.mapUsernameToGameCode(clientHandler.getUsername(), code)));
                publicListOf4.forEach(clientHandler -> clientHandler.setGameController(gameController));
                publicListOf4.clear();
            }
        }
    }


    public void addPrivateGameCode(int gameCode, ClientHandler clientHandler, int numOfPlayer ){
        ArrayList<ClientHandler> list = new ArrayList<ClientHandler>();
        list.add(clientHandler);
        if(numOfPlayer == 2){
            synchronized (privateGameOf2){
                privateGameOf2.put(gameCode, list);
            }

        }
        if(numOfPlayer == 3){
            synchronized (privateGameOf3){
                privateGameOf3.put(gameCode, list);
            }

        }
        if(numOfPlayer == 4){
            synchronized (privateGameOf4){
                privateGameOf4.put(gameCode, list);
            }
        }
    }
    public void addInAPrivateGame(int gameCode, ClientHandler clientHandler){
        boolean flag = false;
        synchronized (privateGameOf2){
            if(privateGameOf2.containsKey(gameCode)){
                ArrayList<ClientHandler> list = privateGameOf2.get(gameCode);
                list.add(clientHandler);
                privateGameOf2.put(gameCode, list);
                if(list.size()==2){
                    createPrivateGame(list, gameCode, 2);
                }
                flag = true;
            }
        }
        synchronized (privateGameOf3){
            if(!flag && privateGameOf3.containsKey(gameCode)){
                ArrayList<ClientHandler> list = privateGameOf3.get(gameCode);
                list.add(clientHandler);
                privateGameOf3.put(gameCode, list);
                if(list.size()==3){
                    createPrivateGame(list, gameCode, 3);
                }
                flag = true;
            }
        }
        synchronized (privateGameOf4){
            if(!flag && privateGameOf4.containsKey(gameCode)){
                ArrayList<ClientHandler> list = privateGameOf4.get(gameCode);
                list.add(clientHandler);
                privateGameOf4.put(gameCode, list);
                if(list.size()==4){
                    createPrivateGame(list, gameCode, 4);
                }
                flag = true;
            }
        }

        if(!flag)
                clientHandler.sendMessage(new Message(clientHandler.getUsername(), MessageType.CHOOSE_GAME_MODE ));


    }
    public void createPrivateGame(ArrayList<ClientHandler> list, int gameCode, int numOfPlayer){
        System.out.println("sono dentro alla createPrivateGame");
        GameController gameController = new GameController(list, usernameIssuer, gameCodeIssuer);
        gameController.setGameCode(gameCode);
        gameCodeIssuer.associatePrivateCodeTo(gameController, gameCode);
        list.forEach((clientHandler -> this.usernameIssuer.mapUsernameToGameCode(clientHandler.getUsername(), gameCode)));
        list.forEach(clientHandler -> clientHandler.setGameController(gameController));
        gameController.initGameEnv();
        gameController.startGameTurn();
        if(numOfPlayer == 2){  // devo rimuovere la lista dalla hashMap nella lobby
            privateGameOf2.remove(gameCode);
        }
        if(numOfPlayer == 3){  // devo rimuovere la lista dalla hashMap nella lobby
            privateGameOf3.remove(gameCode);
        }
        if(numOfPlayer == 4){  // devo rimuovere la lista dalla hashMap nella lobby
            privateGameOf4.remove(gameCode);
        }
    }

    /**
     * TODO: remove clientHandler from the lobby gives a ConcurrentModificationException
     * @param clientHandler
     */
    public void disconnect(ClientHandler clientHandler) {
        boolean flag = false;
        int i;
        // find client handler in one of the lobbies
        // and delete
        synchronized (publicListOf2){
            for (i = 0; i < publicListOf2.size(); i ++) {
                //clientHandler and c refer to the same object.
                if (publicListOf2.get(i) == clientHandler) {
                    publicListOf2.remove(i);
                    System.out.println ("(LobbyController) removed client: " + clientHandler.getUsername() + " from lobby of 2");
                    flag = true;
                }
            }
        }

        if (!flag) {
            synchronized (publicListOf3){
                for (i = 0; i < publicListOf3.size(); i ++) {
                    //clientHandler and c refer to the same object.
                    if (publicListOf3.get(i) == clientHandler) {
                        publicListOf3.remove(i);
                        System.out.println ("(LobbyController) removed client: " + clientHandler.getUsername() + " from lobby of 3");
                        flag = true;
                    }
                }
            }

        }

        if (!flag) {
            synchronized (publicListOf4){
                for (i = 0; i < publicListOf4.size(); i ++) {
                    //clientHandler and c refer to the same object.
                    if (publicListOf4.get(i) == clientHandler) {
                        publicListOf4.remove(i);
                        System.out.println ("(LobbyController) removed client: " + clientHandler.getUsername() + " from lobby of 4");
                        flag = true;
                    }
                }
            }

        }
        if (!flag) {
            System.out.println("(LobbyController) client: " + clientHandler.getUsername() + " wasn't in lobby");
        }
    }
}
