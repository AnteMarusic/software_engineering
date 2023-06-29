package org.polimi.servernetwork.controller;

import org.junit.jupiter.api.Test;
import org.polimi.messages.Message;

import static org.junit.jupiter.api.Assertions.*;

class LobbyControllerTest {
    public static class ClientHandlerStub extends ClientHandler {

        public ClientHandlerStub(UsernameIssuer usernameIssuer, GameCodeIssuer gameCodeIssuer, LobbyController lobbyController) {
            super(usernameIssuer, gameCodeIssuer, lobbyController);
        }

        @Override
        public void sendMessage(Message message) {
            System.out.println();
        }

        @Override
        public void reconnection() {

        }

        @Override
        protected void closeEverything() {

        }
    }

    @Test
    void insertPlayer() {
    }
}