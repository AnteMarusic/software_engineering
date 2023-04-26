package org.polimi.messages;

public class ChooseGameModeMessage extends Message {
    private GameMode gameMode;
    private int numOfPlayers;

    public ChooseGameModeMessage(String username, GameMode gameMode, int numOfPlayers) {
        super(username, MessageType.CHOOSE_GAME_MODE);
        this.gameMode = gameMode;
        this.numOfPlayers = numOfPlayers;
    }

    @Override
    public String toString() {
        return super.toString() + "ChooseGameModeMessage{" +
                "gameMode=" + gameMode +
                ", numOfPlayers=" + numOfPlayers +
                '}';
    }
}