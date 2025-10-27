package com.weiyi.demoWordle.service;

import com.weiyi.demoWordle.entity.*;

public interface GameService {
    /** Start a new game session for a chosen level **/
    GameSession startNewGame(GameLevel level, GameMode mode);

    /** Make a guess within an existing session **/
    FeedbackResult makeGuess(String sessionId, String guess);

    /** Retrieve the current session state (history, rounds, status) **/
    GameSession getSession(String sessionId);

    /** Start a new game session when the server is cheating **/
    GameSession startGameCheating(GameLevel level);

    /** Make a guess within an existing session under cheating circumstance **/
    FeedbackResult makeGuessCheating(String sessionId, String guess);

//    GameStartResponse startMultiplayerGame(GameLevel level, String playerId);
    GameRoom startMultiplayerGame(GameLevel level, String playerId);

//    GameStartResponse joinGame(String sessionId, String playerId);
    GameRoom joinGame(String roomId, String playerId);

    FeedbackResult makeMultiplayerGuess(String sessionId, String playerId, String guess);


}
