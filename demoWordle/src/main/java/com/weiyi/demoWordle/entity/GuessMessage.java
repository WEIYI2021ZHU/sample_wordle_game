package com.weiyi.demoWordle.entity;

import lombok.Data;

@Data
public class GuessMessage {
    private String roomId;
    private String playerId;
    private String guess;

    public String getSessionId() {
        return roomId;
    }

    public void setSessionId(String sessionId) {
        this.roomId = sessionId;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getGuess() {
        return guess;
    }

    public void setGuess(String guess) {
        this.guess = guess;
    }
}
