package org.polimi.messages;

public enum MessageType {
    PING,
    CHOOSE_GAME_MODE,
    USERNAME,
    TEXT_MESSAGE,
    NOTIFY_NEW_PLAYER_JOINED,
    CHOOSE_CARDS_REQUEST,
    CHOSEN_CARDS_REPLY,
    CHOOSE_COLUMN_REQUEST,
    CHOSEN_COLUMN_REPLY,
    INFORM_ABOUT_NEXT_TURN,
    NOTIFY_GOAL_COMPLETION,
    NOTIFY_GAME_END,
    ERROR_MESSAGE,
}
