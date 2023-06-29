package org.polimi.client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.polimi.client.view.gui.Gui;
import org.polimi.client.view.gui.sceneControllers.GameLoopSceneController;
import org.polimi.client.view.gui.sceneControllers.SceneController;
import org.polimi.messages.*;

import java.io.IOException;
import java.rmi.NotBoundException;

import static org.junit.jupiter.api.Assertions.*;

class GuiClientControllerTest {
    private GuiClientController guiClientController;
    private TestRMIClient stubClient;

    private static class TestRMIClient extends RMIClient {
        public TestRMIClient(int port, boolean guiMode) throws IOException, NotBoundException {
            super(port, guiMode);
        }

        // Override the sendMessage method
        public void sendMessage(String message) {
            // Do nothing in the test implementation
        }
    }


    @BeforeEach
    public void setup() throws NotBoundException, IOException {
        stubClient = new TestRMIClient(1099, true);
        guiClientController = new GuiClientController(stubClient, true);
    }
    @Test
    public void testHandleMessage_ChooseGameModeMessageType() throws IOException {
        ChosenGameModeMessage message = new ChosenGameModeMessage("testUser", GameMode.JOIN_RANDOM_GAME_3_PLAYER,-1);
        assertFalse(guiClientController.isReceivedGameModeMess());
        guiClientController.handleMessage(message);
        assertTrue(guiClientController.isReceivedGameModeMess());
    }
    @Test
    public void testHandleMessage_ModelStatusAll() {
    }
    @Test
    void waitForFlag() {
    }

    @Test
    void reset() {
    }

    @Test
    void getNotified() {
    }

    @Test
    void setUsername() {
    }

    @Test
    void chooseUsername() {
    }

    @Test
    void chooseCards() {
    }

    @Test
    void removeOtherPlayerCards() {
    }

    @Test
    void insertInOtherPlayerBookshelf() {
    }

    @Test
    void alreadyTakenUsername() {
    }

    @Test
    void chooseGameMode() {
    }

    @Test
    void orderChosenCards() {
    }

    @Test
    void chooseColumn() {
    }

    @Test
    void errorMessage() {
    }

    @Test
    void newPlayerJoinedLobby() {
    }

    @Test
    void loginSuccessful() {
    }

    @Test
    void reconnectionSuccessful() {
    }

    @Test
    void modelAllMessage() {
    }

    @Test
    void disconnect() {
    }
}