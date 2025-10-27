package com.weiyi.demoWordle.exception;

public class PlayerException extends GameException{
    public PlayerException(String playerId) {
        super("Player "+ playerId + " already joined.");
    }
}
