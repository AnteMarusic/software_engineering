package org.polimi.messages;

public enum GameMode {
    DEFAULT,
    CREATE_PRIVATE_GAME,
    JOIN_PRIVATE_GAME,
    JOIN_RANDOM_GAME_2_PLAYER,
    JOIN_RANDOM_GAME_3_PLAYER,
    JOIN_RANDOM_GAME_4_PLAYER;

    public static int convertToInt(GameMode gamemode){
        switch(gamemode){
            case DEFAULT ->{
                return -1;
            }
            case CREATE_PRIVATE_GAME ->{
                return 0;
            }
            case JOIN_PRIVATE_GAME ->{
                return 1;
            }
            case JOIN_RANDOM_GAME_2_PLAYER ->{
                return 2;
            }
            case JOIN_RANDOM_GAME_3_PLAYER ->{
                return 3;
            }
            case JOIN_RANDOM_GAME_4_PLAYER ->{
                return 4;
            }

        }
        return 0;
    }

}
