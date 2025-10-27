package com.weiyi.demoWordle.service.impl;

import com.weiyi.demoWordle.entity.*;
import com.weiyi.demoWordle.exception.InvalidLetterException;
import com.weiyi.demoWordle.exception.InvalidLengthException;
import com.weiyi.demoWordle.exception.InvalidWordException;
import com.weiyi.demoWordle.exception.SessionNotFoundException;
import com.weiyi.demoWordle.service.GameService;
import com.weiyi.demoWordle.service.LevelWordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GameServiceImpl implements GameService {

    private LevelWordService theLevelWordService;
    private Map<String, GameSession> sessions;

    @Autowired
    public GameServiceImpl(LevelWordService theLevelWordService) {
        this.theLevelWordService = theLevelWordService;
        this.sessions = new HashMap<>();
    }


    @Override
    public GameSession startNewGame(GameLevel level, GameMode mode) {
        String word = theLevelWordService.getRandomWordByLevel(level);
        int maxRounds = switch (level) {
            case EASY -> 8;
            case MEDIUM -> 6;
            case HARD -> 5;
            case EXPERT -> 4;
        };

        GameSession session = new GameSession(word, maxRounds);
        session.setLevel(level);
        session.setMode(mode);
        String sessionId = UUID.randomUUID().toString();
        session.setSessionId(sessionId);

        if (mode == GameMode.MULTIPLE) {
            session.setPlayerIds(new ArrayList<>());
            session.setScores(new HashMap<>());
        }

        sessions.put(sessionId, session);

        // used to test the method
//        System.out.printf("Started new %s game (Session: %s) with word: %s%n", level, sessionId, word);
        return session;
    }

    @Override
    public FeedbackResult makeGuess(String sessionId, String guess) {
        GameSession session = sessions.get(sessionId);
        // check if this session is valid
        if (session == null) {
            throw new SessionNotFoundException(sessionId);
        }
        if (guess == null) {
            throw new RuntimeException("Please enter a five-letter word");
        }
        if (guess.length() != 5) {
            throw new InvalidLengthException(5, guess.length());
        }
        // check if the guess contains digits
        if (!guess.matches("[a-zA-Z]+")) {
            throw new InvalidLetterException(guess);
        }
        guess = guess.toLowerCase();
        // check if the guess is in the dictionary
        if (!theLevelWordService.getWords().contains(guess)) {
            throw new InvalidWordException(guess);
        }
        FeedbackResult result = session.makeGuess(guess);

        // Save updated session
        sessions.put(sessionId, session);
        return result;
    }


    @Override
    public GameSession getSession(String sessionId) {
        GameSession session = sessions.get(sessionId);
        if (session == null) {
            throw new SessionNotFoundException(sessionId);
        }
        return session;
    }

    @Override
    public GameSession startGameCheating(GameLevel level) {
        List<String> words = theLevelWordService.getRandomWordsByLevel(level);
        int maxRounds = switch (level) {
            case EASY -> 8;
            case MEDIUM -> 6;
            case HARD -> 5;
            case EXPERT -> 4;
        };

//        GameSession session = new GameSession(word, maxRounds);
//        session.setLevel(level);
//        session.setMode(mode);
//        String sessionId = UUID.randomUUID().toString();
//        session.setSessionId(sessionId);
        return null;
    }

    @Override
    public FeedbackResult makeGuessCheating(String sessionId, String guess) {
        return null;
    }


}
