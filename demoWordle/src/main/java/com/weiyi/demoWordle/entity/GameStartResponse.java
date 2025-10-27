package com.weiyi.demoWordle.entity;

// designed for multiple users
public class GameStartResponse {
    private String sessionId;
    private int maxRounds;
    private GameLevel level;
    private GameMode mode;
    private GameStatus status;

    public GameStartResponse() {
    }

    public GameStartResponse(String sessionId, int maxRounds, GameLevel level, GameMode mode, GameStatus status) {
        this.sessionId = sessionId;
        this.maxRounds = maxRounds;
        this.level = level;
        this.mode = mode;
        this.status = status;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public int getMaxRounds() {
        return maxRounds;
    }

    public void setMaxRounds(int maxRounds) {
        this.maxRounds = maxRounds;
    }

    public GameLevel getLevel() {
        return level;
    }

    public void setLevel(GameLevel level) {
        this.level = level;
    }

    public GameMode getMode() {
        return mode;
    }

    public void setMode(GameMode mode) {
        this.mode = mode;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }
}
