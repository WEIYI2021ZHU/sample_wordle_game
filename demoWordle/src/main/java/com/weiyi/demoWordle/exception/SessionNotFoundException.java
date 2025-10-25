package com.weiyi.demoWordle.exception;

public class SessionNotFoundException extends GameException{
    public SessionNotFoundException(String sessionId) {
        super("No game session found for ID: " + sessionId);
    }
}
