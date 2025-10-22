package com.weiyi.demoWordle.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WordServiceImpl implements WordService{


    @Override
    public List<String> getAllWords() {
        return null;
    }

    @Override
    public String getRandomWord() {
        return null;
    }

    @Override
    public String getDailyWord() {
        return null;
    }

    @Override
    public boolean isValidWord(String word) {
        return false;
    }
}
