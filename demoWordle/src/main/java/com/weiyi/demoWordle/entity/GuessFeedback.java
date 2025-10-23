package com.weiyi.demoWordle.entity;

import java.util.List;
public class GuessFeedback {
    private String guess;
    private List<LetterFeedback> letters;
    private GameStatus status;

    public GuessFeedback(String guess, List<LetterFeedback> letters, GameStatus status) {
        this.guess = guess;
        this.letters = letters;
        this.status = status;
    }

    public String getGuess() {
        return guess;
    }

    public void setGuess(String guess) {
        this.guess = guess;
    }

    public List<LetterFeedback> getLetters() {
        return letters;
    }

    public void setLetters(List<LetterFeedback> letters) {
        this.letters = letters;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }
}
