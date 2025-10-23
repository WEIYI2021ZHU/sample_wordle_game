package com.weiyi.demoWordle.entity;
import java.util.List;
public class FeedbackResult {
    private String guess;
    private List<String> colors;
    private GameStatus status;

    public FeedbackResult(String guess, List<String> colors, GameStatus status) {
        this.guess = guess;
        this.colors = colors;
        this.status = status;
    }

    public String getGuess() {
        return guess;
    }

    public List<String> getColors() {
        return colors;
    }

    public GameStatus getStatus() {
        return status;
    }
}
