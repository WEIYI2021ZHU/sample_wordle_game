package com.weiyi.demoWordle.rest;

import com.weiyi.demoWordle.entity.*;
import com.weiyi.demoWordle.exception.GameException;
import com.weiyi.demoWordle.exception.InvalidLetterException;
import com.weiyi.demoWordle.service.GameService;
import com.weiyi.demoWordle.exception.InvalidLengthException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;


import java.util.HashMap;
import java.util.Map;

@Controller
public class WebSocketGameController {

//    private final MultiplayerService multiplayerService;
//    private final SimpMessagingTemplate messagingTemplate;
//
//    public WebSocketGameController(MultiplayerService multiplayerService, SimpMessagingTemplate messagingTemplate) {
//        this.multiplayerService = multiplayerService;
//        this.messagingTemplate = messagingTemplate;
//    }
//
//    @MessageMapping("/guess")
//    public void handleGuess(@Payload Map<String, String> payload) {
//        String sessionId = payload.get("sessionId");
//        String playerId = payload.get("playerId");
//        String guess = payload.get("guess");
//        var feedback = multiplayerService.makeGuess(sessionId, playerId, guess);
//        messagingTemplate.convertAndSend("/topic/game/" + sessionId, feedback);
//    }
//
//    @MessageMapping("/join")
//    public void handleJoin(@Payload Map<String, String> payload) {
//        String sessionId = payload.get("sessionId");
//        String playerId = payload.get("playerId");
//        multiplayerService.joinGame(sessionId, playerId);
//        messagingTemplate.convertAndSend("/topic/game/" + sessionId,
//                Map.of("message", "Player " + playerId + " joined the game"));
//    }

    @Autowired
    private GameService gameService;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/join")
    public void join(@Payload JoinMessage msg) {
        try {
            GameRoom resp = gameService.joinGame(msg.getRoomId(), msg.getPlayerId());
            messagingTemplate.convertAndSend("/topic/wordle/" + msg.getRoomId(), resp);
        }
        catch (GameException e) {
            messagingTemplate.convertAndSendToUser(
                    msg.getPlayerId(), "/queue/errors",
                    Map.of("error", e.getMessage())
            );
        }

    }

    @MessageMapping("/guess")
    public void guess(@Payload GuessMessage msg) {
        try{
            String guess = msg.getGuess();

            if (guess == null) {
                throw new RuntimeException("Please enter a five-letter word");
            }
            if (guess.length() != 5) {
                throw new InvalidLengthException(5, guess.length());
            }
            // check if the guess contains digits
            if (!guess.matches("[a-zA-Z]+")) {
                throw new InvalidLetterException(guess);
            }

            FeedbackResult feedback = gameService.makeMultiplayerGuess(
                    msg.getRoomId(), msg.getPlayerId(), msg.getGuess());
            messagingTemplate.convertAndSend("/topic/wordle/" + msg.getRoomId(), feedback);
        }
        catch (Exception ex) {
            Map<String, Object> errorMsg = new HashMap<>();
            errorMsg.put("error", true);
            errorMsg.put("text", ex.getMessage());
            messagingTemplate.convertAndSend("/topic/wordle/" + msg.getRoomId(), errorMsg);
            ex.printStackTrace();
        }


    }

    @MessageExceptionHandler
    public void handleWebSocketException(Exception ex) {
        // For debugging
        ex.printStackTrace();

        // Optional: send to a global error topic
        messagingTemplate.convertAndSend("/topic/errors", Map.of(
                "error", true,
                "message", ex.getMessage()
        ));
    }
}
