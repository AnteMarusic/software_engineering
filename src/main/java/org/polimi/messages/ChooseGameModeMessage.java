package org.polimi.messages;

public class ChooseGameModeMessage extends Message {
    private GameMode gameMode;

    public ChooseGameModeMessage(String username, GameMode gameMode) {
        super(username, MessageType.CHOOSE_GAME_MODE);
        this.gameMode = gameMode;
    }

    public GameMode getGameMode(){
        return this.gameMode;
    }

    @Override
    public String toString() {
        return super.toString() + "ChooseGameModeMessage{" +
                "gameMode=" + gameMode;
    }
}