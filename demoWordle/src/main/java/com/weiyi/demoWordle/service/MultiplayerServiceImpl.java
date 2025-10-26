package com.weiyi.demoWordle.service;

import com.weiyi.demoWordle.entity.FeedbackResult;
import org.springframework.stereotype.Service;


@Service
public class MultiplayerServiceImpl implements MultiplayerService{

    @Override
    public FeedbackResult makeGuess(String sessionId, String playerId, String guess) {
        return null;
    }

    @Override
    public void joinGame(String sessionId, String playerId) {

    }
}
