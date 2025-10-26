package com.weiyi.demoWordle.rest;

import com.weiyi.demoWordle.service.MultiplayerService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.Map;

@Controller
public class WebSocketGameController {

    private final MultiplayerService multiplayerService;
    private final SimpMessagingTemplate messagingTemplate;

    public WebSocketGameController(MultiplayerService multiplayerService, SimpMessagingTemplate messagingTemplate) {
        this.multiplayerService = multiplayerService;
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/guess")
    public void handleGuess(@Payload Map<String, String> payload) {
        String sessionId = payload.get("sessionId");
        String playerId = payload.get("playerId");
        String guess = payload.get("guess");
        var feedback = multiplayerService.makeGuess(sessionId, playerId, guess);
        messagingTemplate.convertAndSend("/topic/game/" + sessionId, feedback);
    }

    @MessageMapping("/join")
    public void handleJoin(@Payload Map<String, String> payload) {
        String sessionId = payload.get("sessionId");
        String playerId = payload.get("playerId");
        multiplayerService.joinGame(sessionId, playerId);
        messagingTemplate.convertAndSend("/topic/game/" + sessionId,
                Map.of("message", "Player " + playerId + " joined the game"));
    }
}
