package com.weiyi.demoWordle.service.impl;

import com.weiyi.demoWordle.entity.FeedbackResult;
import com.weiyi.demoWordle.service.MultiplayerService;
import org.springframework.stereotype.Service;


@Service
public class MultiplayerServiceImpl implements MultiplayerService {

    @Override
    public FeedbackResult makeGuess(String sessionId, String playerId, String guess) {
        return null;
    }

    @Override
    public void joinGame(String sessionId, String playerId) {

    }
}
