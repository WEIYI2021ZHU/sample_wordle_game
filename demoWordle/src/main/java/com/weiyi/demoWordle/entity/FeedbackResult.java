package com.weiyi.demoWordle.entity;
import java.util.List;
public class FeedbackResult {
    private String guess;
    private List<String> colors;
    private GameStatus status;

    private String answer;
    private String message;


    public FeedbackResult(String guess, List<String> colors, GameStatus status, String answer) {
        this.guess = guess;
        this.colors = colors;
        this.status = status;
        this.answer = answer;
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

    public String getAnswer() {
        return answer;
    }

    public void setGuess(String guess) {
        this.guess = guess;
    }

    public void setColors(List<String> colors) {
        this.colors = colors;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
