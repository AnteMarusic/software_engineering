package org.polimi.messages;

public class SharedPointQueueTOPMessage extends Message{
    private int[] sharedPoint;

    public SharedPointQueueTOPMessage(String username, int[] sharedPoint){
        super(username, MessageType.SHAREDPOINTQUEUETOPMESSAGE);
        this.sharedPoint = sharedPoint;
    }

    public int[] getSharedPoint(){
            return this.sharedPoint;
    }
}
