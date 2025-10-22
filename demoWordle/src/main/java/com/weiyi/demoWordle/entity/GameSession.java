package com.weiyi.demoWordle.entity;

public class GameSession {
    private String answer;
    private int currentRound;
    private int maxRounds;
    private GameStatus status;

    public GameSession(String answer, int maxRounds) {
        this.answer = answer;
        this.maxRounds = maxRounds;
        this.currentRound = 0;
        this.status = GameStatus.IN_PROGRESS;
    }

    public void nextRound() {
        this.currentRound++;
        if (currentRound >= maxRounds && status == GameStatus.IN_PROGRESS) {
            status = GameStatus.LOSE;
        }
    }

    public boolean checkGuess(String guess) {
        if (guess.equalsIgnoreCase(answer)) {
            status = GameStatus.WIN;
            return true;
        }
        nextRound();
        return false;
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

    public GameStatus getStatus() {
        return status;
    }
}
