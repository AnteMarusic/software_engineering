package org.polimi.messages;

import java.io.Serializable;

public class ErrorMessage extends Message implements Serializable {
    private ErrorType errorType;
    public ErrorMessage(String username, ErrorType errorType){
        super(username, MessageType.ERROR_MESSAGE);
        this.errorType = errorType;
    }

    public ErrorType getErrorType () {
        return this.errorType;
    }

}
