package com.weiyi.demoWordle.rest;


import com.weiyi.demoWordle.entity.*;
import com.weiyi.demoWordle.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

// connect with the frontend
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/wordle")
public class WordController {

    private GameService gameService;

    @Autowired
    public WordController(GameService gameService) {
        this.gameService = gameService;
    }

//    @Autowired
//    public WordController(GameService gameService, MultiplayerService multiplayerService) {
//        this.gameService = gameService;
//        this.multiplayerService = multiplayerService;
//    }


//
//    // test webpage to show all the words we have
//    @GetMapping("/words")
//    public List<String> getWord() {
//        return wordService.getAllWords();
//    }

    // At the start, user could choose a level of difficulty
    @PostMapping("/start")
    public GameSession startGame(@RequestParam GameLevel level,
                                 @RequestParam GameMode mode) {
        return gameService.startNewGame(level, mode);
    }

//    @PostMapping("/start")
//    public GameStartResponse startGame(@RequestParam GameLevel level,
//                                 @RequestParam GameMode mode) {
//        GameSession session = gameService.startNewGame(level, mode);
//        GameStartResponse response = new GameStartResponse(session.getMaxRounds(), level, mode, session.getStatus());
//        response.setSessionId(session.getSessionId());
//        return response;
//    }

    @PostMapping("/guess/{sessionId}")
    public FeedbackResult makeGuess(@PathVariable String sessionId,
                                    @RequestBody Map<String, String> body) {
        String guess = body.get("guess");
        return gameService.makeGuess(sessionId, guess);
    }

    // get the session by id
    // use to check the current session, history and answers
    @GetMapping("/state/{sessionId}")
    public GameSession getState(@PathVariable String sessionId) {
        GameSession session = gameService.getSession(sessionId);
        return session;
    }

    @PostMapping("/multi/start")
    public GameRoom startMultiplayerGame(
            @RequestParam GameLevel level,
            @RequestParam String playerId) {
        return gameService.startMultiplayerGame(level, playerId);
    }

//    @PostMapping("/multi/join/{sessionId}")
//    public GameStartResponse joinGame(@PathVariable String sessionId, @RequestParam String playerId) {
//        return gameService.joinGame(sessionId, playerId);
//    }


    // Multiplayer endpoints
//    @PostMapping("/multi/start")
//    public GameStartResponse startMultiplayer(@RequestParam GameLevel level) {
//        return gameService.startNewGame(level, GameMode.MULTIPLE);
//    }

    // remind the player that another one has joined the game
//    @PostMapping("/multi/join/{sessionId}")
//    public ResponseEntity<String> joinMultiplayer(@PathVariable String sessionId, @RequestParam String playerId) {
//        gameService.joinGame(sessionId, playerId);
//        return ResponseEntity.ok("Player " + playerId + " joined game " + sessionId);
//    }
////
//    @PostMapping("/multi/guess/{sessionId}")
//    public FeedbackResult makeMultiplayerGuess(
//            @PathVariable String sessionId,
//            @RequestParam String playerId,
//            @RequestBody Map<String, String> body) {
//        String guess = body.get("guess");
//        return gameService.makeMultiplayerGuess(sessionId, playerId, guess);
//    }
}
