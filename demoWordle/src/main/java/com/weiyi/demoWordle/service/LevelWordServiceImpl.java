package com.weiyi.demoWordle.service;

import com.weiyi.demoWordle.entity.GameLevel;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class LevelWordServiceImpl implements LevelWordService{

    private List<String> words = new ArrayList<>();
    private Map<GameLevel, List<String>> levelWords = new HashMap<>();

    private SecureRandom random = new SecureRandom();


    /** read the txt files only once **/
    @PostConstruct
    public void loadWords() {
        words = readWordsFromFile("wordLists/five_letter_words.txt");
        levelWords.put(GameLevel.EASY, readWordsFromFile("wordLists/easy_level_words.txt"));
        levelWords.put(GameLevel.MEDIUM, readWordsFromFile("wordLists/medium_level_words.txt"));
        levelWords.put(GameLevel.HARD, readWordsFromFile("wordLists/hard_level_words.txt"));
        levelWords.put(GameLevel.EXPERT, readWordsFromFile("wordLists/expert_level_words.txt"));
    }


    /** read the data from txt files **/
    private List<String> readWordsFromFile(String fileName) {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        new ClassPathResource(fileName).getInputStream(),
                        StandardCharsets.UTF_8))) {

            return reader.lines()
                    .map(String::trim)
                    .filter(w -> w.length() == 5 && w.matches("[a-zA-Z]+"))
                    .map(String::toLowerCase)
                    .collect(Collectors.toList());

        }
        catch (Exception e) {
            throw new RuntimeException("Failed to load word list", e);
        }
    }

    @Override
    public List<String> getWordsByLevel(GameLevel level) {
        return levelWords.get(level);
    }

    @Override
    public String getRandomWordByLevel(GameLevel level) {
        List<String> theWords = levelWords.get(level);
        // check if the list is empty
        if (theWords.isEmpty()) return null;
        return theWords.get(random.nextInt(theWords.size()));
    }

    @Override
    public List<String> getWords() {
        return words;
    }
}
