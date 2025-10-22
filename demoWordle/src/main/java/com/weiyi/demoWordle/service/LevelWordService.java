package com.weiyi.demoWordle.service;

import com.weiyi.demoWordle.entity.GameLevel;

import java.util.List;

public interface LevelWordService {

    /** Get all words for a specific level */
    List<String> getWordsByLevel(GameLevel level);

    /** Get a random word for a specific level */
    String getRandomWordByLevel(GameLevel level);
}
