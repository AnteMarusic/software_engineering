package org.polimi.server;

import org.polimi.messages.ChooseGameModeMessage;
import org.polimi.server.controller.GameController;
import org.polimi.server.controller.OldGameController;

import java.util.ArrayList;
import java.util.Random;

public class LobbyController {
    private ArrayList<ClientHandler> publicListOf2;
    private ArrayList<ClientHandler> publicListOf3;
    private ArrayList<ClientHandler> publicListOf4;

    GameCodeIssuer gameCodeIssuer;
    UsernameIssuer usernameIssuer;

    public LobbyController(GameCodeIssuer gameCodeIssuer, UsernameIssuer usernameIssuer){
        this.gameCodeIssuer = gameCodeIssuer;
        this.usernameIssuer = usernameIssuer;
    }

    public synchronized void insertPlayer(ClientHandler clientHandler, int gameMode){
        switch(gameMode){
            case 2-> {
                publicListOf2.add(clientHandler);
                if(publicListOf2.size()==2){
                    this.createGame(2);
                }
                else{
                    // comunica al client di essere in attesa di altri giocatori
                }
            }
            case 3-> {
                publicListOf3.add(clientHandler);
                if(publicListOf3.size()==3){
                    this.createGame(3);
                }
                else{
                    // comunica al client di essere in attesa di altri giocatori
                }
            }
            case 4-> {
                publicListOf4.add(clientHandler);
                if(publicListOf3.size()==4){
                    this.createGame(4);
                }
                else{
                    // comunica al client di essere in attesa di altri giocatori
                }
            }
        }
    }

    private void createGame(int gameMode){
        Random random = new Random();

        int i = random.nextInt(12);
        switch (gameMode) {
            case 2-> {
                GameController gameController = new GameController(publicListOf2);
                int code = gameCodeIssuer.associateCodeTo(gameController);
                publicListOf2.forEach((clientHandler -> this.usernameIssuer.mapUsernameToGameCode(clientHandler.getUsername(), code)));
                publicListOf2.forEach(clientHandler -> clientHandler.setGameController(gameController));
                publicListOf2.clear();

                i = random.nextInt(2);
                gameController.setFirstPlayer(i);
            }
            case 3-> {
                GameController gameController = new GameController(publicListOf3);
                int code = gameCodeIssuer.associateCodeTo(gameController);
                publicListOf3.forEach((clientHandler -> this.usernameIssuer.mapUsernameToGameCode(clientHandler.getUsername(), code)));
                publicListOf3.forEach(clientHandler -> clientHandler.setGameController(gameController));
                publicListOf3.clear();
                i = random.nextInt(2);
                gameController.setFirstPlayer(i);
            }
            case 4-> {
                GameController gameController = new GameController(publicListOf4);
                int code = gameCodeIssuer.associateCodeTo(gameController);
                publicListOf4.forEach((clientHandler -> this.usernameIssuer.mapUsernameToGameCode(clientHandler.getUsername(), code)));
                publicListOf4.forEach(clientHandler -> clientHandler.setGameController(gameController));
                publicListOf4.clear();
                i = random.nextInt(2);
                gameController.setFirstPlayer(i);
            }
        }


    }

    public void insertPlayerInRandomTwoPlayerGame(ClientHandler clientHandler) {
        synchronized (publicListOf2) {
            publicListOf2.add(clientHandler);
            if(publicListOf2.size()==2){
                this.createGame(2);
            }
        }
    }

    public void insertPlayerInRandomThreePlayerGame(ClientHandler clientHandler) {
        synchronized (publicListOf3) {
            publicListOf3.add(clientHandler);
            if(publicListOf3.size()==3){
                this.createGame(3);
            }
        }
    }

    public void insertPlayerInRandomFourPlayerGame(ClientHandler clientHandler) {
        synchronized (publicListOf4) {
            publicListOf4.add(clientHandler);
            if(publicListOf4.size()==4){
                this.createGame(4);
            }
        }
    }
}
