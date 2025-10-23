package com.weiyi.demoWordle.service;

import com.weiyi.demoWordle.entity.FeedbackResult;
import com.weiyi.demoWordle.entity.GameLevel;
import com.weiyi.demoWordle.entity.GameSession;
import com.weiyi.demoWordle.entity.GuessFeedback;

public interface GameService {
    // Start a new game session for a chosen level
    GameSession startNewGame(GameLevel level);

    // Make a guess within an existing session
    FeedbackResult makeGuess(String sessionId, String guess);

    // Retrieve the current session state (history, rounds, status)
    GameSession getSession(String sessionId);
}
