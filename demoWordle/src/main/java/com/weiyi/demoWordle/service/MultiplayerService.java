package com.weiyi.demoWordle.service;

import com.weiyi.demoWordle.entity.FeedbackResult;

public interface MultiplayerService {
    FeedbackResult makeGuess(String sessionId, String playerId, String guess);
    void joinGame(String sessionId, String playerId);
}
