package org.polimi.server.controller;

import org.polimi.messages.Message;
import org.polimi.messages.MessageType;

import java.util.ArrayList;

public class LobbyController {
    //to synchronize and add update message
    private final ArrayList<ClientHandler> publicListOf2;
    private final ArrayList<ClientHandler> publicListOf3;
    private final ArrayList<ClientHandler> publicListOf4;

    GameCodeIssuer gameCodeIssuer;
    UsernameIssuer usernameIssuer;

    public LobbyController(GameCodeIssuer gameCodeIssuer, UsernameIssuer usernameIssuer){
        this.gameCodeIssuer = gameCodeIssuer;
        this.usernameIssuer = usernameIssuer;
        this.publicListOf2 = new ArrayList<>(2);
        this.publicListOf3 = new ArrayList<>(3);
        this.publicListOf4 = new ArrayList<>(4);
    }

    private void printLobby (int gameMode) {
        switch (gameMode) {
            case 2 -> {
                System.out.print("lobby of two: ");
                System.out.print("size: " + publicListOf2.size() + "contains: ");
                for (ClientHandler c : publicListOf2) {
                    System.out.print(c.getUsername());
                }
                System.out.println();
             }
            case 3 -> {
                System.out.print("lobby of three: ");
                System.out.print("size: " + publicListOf3.size() + "contains: ");
                for (ClientHandler c : publicListOf3) {
                    System.out.print(c.getUsername());
                }
                System.out.println();
            }
            case 4 -> {
                System.out.print("lobby of four: ");
                System.out.print("size: " + publicListOf4.size() + "contains: ");
                for (ClientHandler c : publicListOf4) {
                    System.out.print(c.getUsername());
                }
                System.out.println();
            }
        }
    }
    public void insertPlayer(ClientHandler clientHandler, int gameMode){
        switch(gameMode){
            case 2-> {
                synchronized (publicListOf2) {
                    publicListOf2.add(clientHandler);
                    printLobby(gameMode);
                    if(publicListOf2.size()==2){
                        this.createGame(2);
                    }
                    else {
                        clientHandler.sendMessage(new Message("server", MessageType.WAITING_IN_LOBBY));
                    }
                }
            }
            case 3-> {
                synchronized (publicListOf3) {
                    publicListOf3.add(clientHandler);
                    printLobby(gameMode);
                    if(publicListOf3.size()==3){
                        this.createGame(3);
                    }
                    else{
                        clientHandler.sendMessage(new Message("server", MessageType.WAITING_IN_LOBBY));
                    }
                }
            }
            case 4-> {
                synchronized (publicListOf4) {
                    publicListOf4.add(clientHandler);
                    printLobby(gameMode);
                    if(publicListOf4.size()==4){
                        this.createGame(4);
                    }
                    else{
                        clientHandler.sendMessage(new Message("server", MessageType.WAITING_IN_LOBBY));
                    }
                }
            }
        }
    }

    private void createGame(int gameMode){
        switch (gameMode) {
            case 2-> {
                GameController gameController = new GameController(publicListOf2);
                int code = gameCodeIssuer.associateCodeTo(gameController);
                publicListOf2.forEach((clientHandler -> this.usernameIssuer.mapUsernameToGameCode(clientHandler.getUsername(), code)));
                publicListOf2.forEach(clientHandler -> clientHandler.setGameController(gameController));
                publicListOf2.clear();
            }
            case 3-> {
                GameController gameController = new GameController(publicListOf3);
                int code = gameCodeIssuer.associateCodeTo(gameController);
                publicListOf3.forEach((clientHandler -> this.usernameIssuer.mapUsernameToGameCode(clientHandler.getUsername(), code)));
                publicListOf3.forEach(clientHandler -> clientHandler.setGameController(gameController));
                publicListOf3.clear();
            }
            case 4-> {
                GameController gameController = new GameController(publicListOf4);
                int code = gameCodeIssuer.associateCodeTo(gameController);
                publicListOf4.forEach((clientHandler -> this.usernameIssuer.mapUsernameToGameCode(clientHandler.getUsername(), code)));
                publicListOf4.forEach(clientHandler -> clientHandler.setGameController(gameController));
                publicListOf4.clear();
            }
        }
    }

    public void disconnect(ClientHandler clientHandler) {
        boolean flag = false;
        // find client handler in one of the lobbies
        // and delete
        for (ClientHandler c : publicListOf2) {
            //clientHandler and c refer to the same object.
            if (c == clientHandler) {
                publicListOf2.remove(c);
                flag = true;
            }
        }

        if (!flag) {
            for (ClientHandler c : publicListOf3) {
                //clientHandler and c refer to the same object.
                if (c == clientHandler) {
                    publicListOf3.remove(c);
                    flag = true;
                }
            }
        }

        if (!flag) {
            for (ClientHandler c : publicListOf4) {
                //clientHandler and c refer to the same object.
                if (c == clientHandler) {
                    publicListOf4.remove(c);
                    flag = true;
                }
            }
        }
        if (flag) {
            System.out.println ("removed client: " + clientHandler.getUsername() + "from lobby");
        }
        else {
            System.out.println("client: " + clientHandler.getUsername() + "wasn't in lobby");
        }

    }
}
