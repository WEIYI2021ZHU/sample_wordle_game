package com.weiyi.demoWordle.entity;

import lombok.Data;

// designed for multiple users
@Data
public class GameStartResponse {
    private String sessionId;
    private String roomId;
    private int maxRounds;
    private GameLevel level;
    private GameMode mode;
    private GameStatus status;


    // fields for multiplayer
    private String playerId;
    private String opponentId;
    private int playerCount;

    public GameStartResponse() {
    }

    public GameStartResponse(int maxRounds, GameLevel level, GameMode mode,
                             GameStatus status) {
        this.maxRounds = maxRounds;
        this.level = level;
        this.mode = mode;
        this.status = status;
    }

    public GameStartResponse(int maxRounds, GameLevel level, GameMode mode, GameStatus status, String playerId) {
        this.maxRounds = maxRounds;
        this.level = level;
        this.mode = mode;
        this.status = status;
        this.playerId = playerId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String sessionId) {
        this.roomId = sessionId;
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

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getOpponentId() {
        return opponentId;
    }

    public void setOpponentId(String opponentId) {
        this.opponentId = opponentId;
    }

    public int getPlayerCount() {
        return playerCount;
    }

    public void setPlayerCount(int playerCount) {
        this.playerCount = playerCount;
    }
}
