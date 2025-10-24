package com.weiyi.demoWordle.entity;

import java.util.Map;

// this is the space for multiple users
public class GameRoom {
    String gameId;
    String targetWord;
    int maxRounds;
    Map<String, PlayerState> players;
    GameStatus status;
}
