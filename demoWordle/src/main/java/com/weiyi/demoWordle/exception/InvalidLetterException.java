package com.weiyi.demoWordle.exception;

public class InvalidLetterException extends GameException{
    public InvalidLetterException(String message) {
        super("The guess " + message + " contains elements that are not letters. Please enter a valid word.");
    }
}
