package com.weiyi.demoWordle.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameSession {
    private String answer;
    private int currentRound;
    private final int maxRounds;
    private GameLevel level;
    private GameStatus status;
    private GameMode mode;
    private final List<String> history;
    private final List<List<String>> colorHistory;
    private String sessionId;

    // cheating
    private List<String> candidates = new ArrayList<>();

    // Multiplayer fields
    private List<String> playerIds;  // multiple players
    private Map<String, Integer> scores;  // track each player's score


    // start a new session to guess
    public GameSession(String answer, int maxRounds) {
        this.answer = answer;
        this.maxRounds = maxRounds;
        this.currentRound = 0;
        this.status = GameStatus.IN_PROGRESS;
        this.history = new ArrayList<>();
        this.colorHistory = new ArrayList<>();
        this.playerIds = new ArrayList<>();
        this.scores = new HashMap<>();
    }

    // under normal game
    public FeedbackResult makeGuess(String guess) {
//        guess = guess.toLowerCase();
        currentRound++;

        // check the guess and store the colors of each cell
        List<String> colors = evaluateColors(guess);
        history.add(guess);
        colorHistory.add(colors);

        if (guess.equals(answer)) {
            status = GameStatus.WIN;
        }
        else if (currentRound >= maxRounds) {
            status = GameStatus.LOSE;
        }

        return new FeedbackResult(guess, colors, status, answer);
    }


    private List<String> evaluateColors(String guess) {
        // initially the colors are all gray
        List<String> colors = new ArrayList<>(List.of("gray", "gray", "gray", "gray", "gray"));

        for (int i = 0; i < 5; i++) {
            // First pass: greens. If the letter is right at the position
            if (guess.charAt(i) == answer.charAt(i)) {
                colors.set(i, "green");
            }
            // Second pass: yellows. If the answer contains the letter
            else if (answer.indexOf(guess.charAt(i)) != -1) {
                colors.set(i, "yellow");
            }
        }

        return colors;
    }

    public String getAnswer() {
        return answer;
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public int getMaxRounds() {
        return maxRounds;
    }

    public GameLevel getLevel() {
        return level;
    }

    public void setLevel(GameLevel level) {
        this.level = level;
    }

    public GameStatus getStatus() {
        return status;
    }
    public void setStatus(GameStatus status) {
        this.status = status;
    }

    public GameMode getMode() {
        return mode;
    }

    public void setMode(GameMode mode) {
        this.mode = mode;
    }

    public List<String> getHistory() {
        return history;
    }

    public List<List<String>> getColorHistory() {
        return colorHistory;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }


    public List<String> getPlayerIds() {
        return playerIds;
    }

    public void setPlayerIds(List<String> playerIds) {
        this.playerIds = playerIds;
    }

    public Map<String, Integer> getScores() {
        return scores;
    }

    public void setScores(Map<String, Integer> scores) {
        this.scores = scores;
    }
}
