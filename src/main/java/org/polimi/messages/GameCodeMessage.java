package org.polimi.messages;

public class GameCodeMessage extends Message{
    int gameCode;

    public GameCodeMessage (int gameCode){
        super("server", MessageType.GAME_CODE_MESSAGE);
        this.gameCode = gameCode;
    }
    public int getGameCode(){
        return this.gameCode;
    }
}
