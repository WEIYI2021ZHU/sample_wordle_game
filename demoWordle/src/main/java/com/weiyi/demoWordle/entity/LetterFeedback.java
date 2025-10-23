package com.weiyi.demoWordle.entity;

public class LetterFeedback {
    private char letter;
    private String color;

    public LetterFeedback(char letter, String color) {
        this.letter = letter;
        this.color = color;
    }

    public char getLetter() {
        return letter;
    }

    public void setLetter(char letter) {
        this.letter = letter;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
