package com.weiyi.demoWordle.exception;

public class RoomFullException extends GameException{

    public RoomFullException(String message) {
        super("Room" +message +" is full. Please create a new room or join other rooms.");
    }
}
