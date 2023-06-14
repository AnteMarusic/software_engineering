package org.polimi.servernetwork.controller;

import org.polimi.messages.ErrorMessage;
import org.polimi.messages.ErrorType;
import org.polimi.messages.Message;
import org.polimi.messages.MessageType;

public class SocketLoginHandler implements Runnable {
    @Override
    public void run () {
            InternalComunication internalComunication = usernameIssuer.login(message.getUsername());
            if(internalComunication == InternalComunication.OK) {
                usernameIssuer.setClientHandler(this, message.getUsername());
                this.username = message.getUsername();
                sendMessage(new Message(this.username, MessageType.CHOOSE_GAME_MODE ));
            }
            if(internalComunication == InternalComunication.ALREADY_TAKEN_USERNAME) {
                sendMessage(new ErrorMessage(this.username, ErrorType.ALREADY_TAKEN_USERNAME));
            }
            //to test
            if(internalComunication == InternalComunication.RECONNECTION){
                this.username = message.getUsername();
                int gameId = usernameIssuer.getGameID(message.getUsername());
                GameController gameController = gameCodeIssuer.getGameController(gameId);
                usernameIssuer.setConnect(this.getUsername());
                gameController.reconnect(this);
            }
    }
}
