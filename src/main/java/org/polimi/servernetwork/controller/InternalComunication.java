package org.polimi.servernetwork.controller;

import java.rmi.Remote;

public enum InternalComunication implements Remote {
    OK,
    ALREADY_TAKEN_USERNAME,
    RECONNECTION
}
