package org.polimi.messages;

import java.io.Serializable;

public class UsernameAndGameModeMessage extends Message implements Serializable {
    private GameMode gameMode;
    private int code;

    public UsernameAndGameModeMessage(String username, GameMode gameMode, int code) {
        super(username, MessageType.USERNAME_AND_GAMEMODE);
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
