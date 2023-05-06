package org.polimi.messages;

import java.io.Serializable;

public class ChosenGameModeMessage extends Message implements Serializable {
    private GameMode gameMode;
    private int code;

    public ChosenGameModeMessage(String username, GameMode gameMode, int code) {
        super(username, MessageType.CHOOSE_GAME_MODE);
        this.gameMode = gameMode;
        this.code = code;
    }

    public GameMode getGameMode(){
        return this.gameMode;
    }
    public int getCode() {return this.code;
    }

    @Override
    public String toString() {
        return super.toString() + "ChooseGameModeMessage{" +
                "gameMode=" + gameMode;
    }
}
