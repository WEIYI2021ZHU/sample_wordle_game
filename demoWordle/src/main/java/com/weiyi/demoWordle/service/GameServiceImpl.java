package com.weiyi.demoWordle.service;

import com.weiyi.demoWordle.entity.GameLevel;
import com.weiyi.demoWordle.entity.GameSession;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class GameServiceImpl implements GameService{

    private LevelWordService theLevelWordService;
    private Map<String, GameSession> session;

    public GameServiceImpl() {
    }

    public GameServiceImpl(LevelWordService theLevelWordService) {
        this.theLevelWordService = theLevelWordService;
        this.session = new HashMap<>();
    }


    @Override
    public GameSession startNewGame(GameLevel level) {

        return null;
    }

    @Override
    public GameSession makeGuess(String guess, String sessionId) {
        return null;
    }
}
