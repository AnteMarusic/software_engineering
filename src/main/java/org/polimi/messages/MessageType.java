package org.polimi.messages;

public enum MessageType {
    USERNAME,
    MODEL_STATUS_ALL,
    MODEL_STATUS_UPDATE,

    CHOOSE_GAME_MODE,
    TEXT_MESSAGE,
    NEW_PLAYER_JOINED,
    CHOOSE_CARDS_REQUEST,
    CHOSEN_CARDS_REPLY,
    CHOOSE_COLUMN_REQUEST,
    CHOSEN_COLUMN_REPLY,
    NOTIFY_NEXT_PLAYER,
    NOTIFY_GOAL_COMPLETION,
    NOTIFY_GAME_END,
    ERROR_MESSAGE,
    START_GAME_MESSAGE,
    RANKING_MESSAGE,

    PING,
    WAITING_IN_LOBBY,
    CARD_TO_REMOVE,
    CURRENT_SCORE,
    RECONNECTION_MESSAGE,
    WAITING_FOR_YOUR_TURN,
    USERNAME_AND_GAMEMODE;

}
