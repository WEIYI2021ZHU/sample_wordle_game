package com.weiyi.demoWordle.exception;

public class InvalidLengthException extends GameException{
    public InvalidLengthException(int expected, int actual) {
        super("Your word must have exactly " + expected + " letters (you entered " + actual + ").");
    }
}
