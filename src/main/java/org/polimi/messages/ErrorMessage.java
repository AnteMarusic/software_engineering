package org.polimi.messages;

public class ErrorMessage extends Message {
    private ErrorType errorType;
    public ErrorMessage(String username, ErrorType errorType){
        super(username, MessageType.ERROR_MESSAGE);
        this.errorType = errorType;
    }

    public ErrorType getErrorType () {
        return this.errorType;
    }

}
