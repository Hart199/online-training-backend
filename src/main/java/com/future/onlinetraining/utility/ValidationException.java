package com.future.onlinetraining.utility;

public class ValidationException extends RuntimeException {

    private String[] messages;

    public ValidationException(String[] messages) {
        this.messages = messages;
    }

    public String[] getMessages() {
        return this.messages;
    }
}
