package com.weiyi.demoWordle.exception;

public class GameAlreadyEndedException extends GameException{
    public GameAlreadyEndedException() {
        super("The game has already ended. Please start a new one.");
    }
}
