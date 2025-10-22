package com.weiyi.demoWordle.entity;

import java.util.List;

public class GameConfig {
    private int maxRounds;
    private List<String> words;

    public GameConfig(int maxRounds, List<String> words) {
        this.maxRounds = maxRounds;
        this.words = words;
    }

    public int getMaxRounds() {
        return maxRounds;
    }

    public void setMaxRounds(int maxRounds) {
        this.maxRounds = maxRounds;
    }

    public List<String> getWords() {
        return words;
    }

    public void setWords(List<String> words) {
        this.words = words;
    }
}
