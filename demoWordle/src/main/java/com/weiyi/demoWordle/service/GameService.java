package com.weiyi.demoWordle.service;

import com.weiyi.demoWordle.entity.*;

public interface GameService {
    /** Start a new game session for a chosen level **/
    GameSession startNewGame(GameLevel level, GameMode mode);

    /** Make a guess within an existing session **/
    FeedbackResult makeGuess(String sessionId, String guess);

    /** Retrieve the current session state (history, rounds, status) **/
    GameSession getSession(String sessionId);
}
