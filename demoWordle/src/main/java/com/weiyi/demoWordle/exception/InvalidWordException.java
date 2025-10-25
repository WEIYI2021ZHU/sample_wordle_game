package com.weiyi.demoWordle.exception;

public class InvalidWordException extends GameException{
    public InvalidWordException(String word) {
        super("The word '" + word + "' is not in the dictionary. Please try another one.");
    }
}
