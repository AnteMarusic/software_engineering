package org.polimi.client;

import org.polimi.messages.Message;
import org.polimi.messages.RMIAvailability;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMICallback extends Remote {
   void getNotified() throws RemoteException;
}