package com.weiyi.demoWordle.exception;

public class DigitsFoundException extends GameException{
    public DigitsFoundException(String message) {
        super("The guess " + message + " contains digts. Please enter a valid word.");
    }
}
