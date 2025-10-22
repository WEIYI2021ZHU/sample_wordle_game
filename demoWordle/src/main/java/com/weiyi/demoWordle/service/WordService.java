package com.weiyi.demoWordle.service;

import java.util.List;

public interface WordService {

    // get all the 5-letter words
    List<String> getAllWords();
    String getRandomWord();
    String getDailyWord();
    boolean isValidWord(String word);
}
