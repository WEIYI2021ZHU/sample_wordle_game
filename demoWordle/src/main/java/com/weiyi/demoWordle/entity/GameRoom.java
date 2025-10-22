package com.weiyi.demoWordle.entity;

import java.util.Map;

public class GameRoom {
    String gameId;
    String targetWord;
    int maxRounds;
    Map<String, PlayerState> players;
    GameStatus status;
}
