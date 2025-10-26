package com.weiyi.demoWordle.config;

import com.weiyi.demoWordle.entity.GameLevel;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class GameConfig {
    private final Map<GameLevel, Integer> maxRoundsPerLevel = Map.of(
            GameLevel.EASY, 8,
            GameLevel.MEDIUM, 6,
            GameLevel.HARD, 5,
            GameLevel.EXPERT, 4
    );

    public int getMaxRounds(GameLevel level) {
        // default to medium level
        return maxRoundsPerLevel.getOrDefault(level, 6);
    }

    public int getWordLength() { return 5; }
}

