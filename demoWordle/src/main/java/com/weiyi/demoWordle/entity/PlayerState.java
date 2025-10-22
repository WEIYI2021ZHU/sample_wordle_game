package com.weiyi.demoWordle.entity;

import java.util.List;
public class PlayerState {
    String playerId;
    int currentRound;
    List<String> guesses;
    GameResult result;
}
