package com.weiyi.demoWordle.service;

import com.weiyi.demoWordle.entity.GameLevel;
import com.weiyi.demoWordle.entity.GameSession;

public interface GameService {
    GameSession startNewGame(GameLevel level);
    GameSession makeGuess(String guess, String sessionId);
}
