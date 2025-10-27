package com.weiyi.demoWordle.entity;

import java.util.ArrayList;
import java.util.List;

public class GameSession {
    private String answer;
    private int currentRound;
    private final int maxRounds;
    private GameLevel level;
    private GameStatus status;
    private final List<String> history = new ArrayList<>();
    private final List<List<String>> colorHistory = new ArrayList<>();
    private String sessionId;



    // start a new session to guess
    public GameSession(String answer, int maxRounds) {
        this.answer = answer;
        this.maxRounds = maxRounds;
        this.currentRound = 0;
        this.status = GameStatus.IN_PROGRESS;
    }

    // new session for multiple players


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

}
