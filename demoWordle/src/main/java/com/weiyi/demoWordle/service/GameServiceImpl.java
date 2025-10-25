package com.weiyi.demoWordle.service;

import com.weiyi.demoWordle.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GameServiceImpl implements GameService{

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
        if (session == null) throw new IllegalStateException("No active session found for ID: " + sessionId);

        FeedbackResult result = session.makeGuess(guess);

        // Save updated session
        sessions.put(sessionId, session);
        return result;
    }


    @Override
    public GameSession getSession(String sessionId) {
        GameSession session = sessions.get(sessionId);
        if (session == null) throw new IllegalStateException("Session not found: " + sessionId);
        return session;
    }
}
