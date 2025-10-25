package com.weiyi.demoWordle.rest;


import com.weiyi.demoWordle.entity.FeedbackResult;
import com.weiyi.demoWordle.entity.GameLevel;
import com.weiyi.demoWordle.entity.GameMode;
import com.weiyi.demoWordle.entity.GameSession;
import com.weiyi.demoWordle.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

// separate from the frontend
//@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/wordle")
public class WordController {

    private GameService gameService;

    @Autowired
    public WordController(GameService gameService) {
        this.gameService = gameService;
    }


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

    @PostMapping("/guess/{sessionId}")
    public FeedbackResult makeGuess(@PathVariable String sessionId,
                                    @RequestBody Map<String, String> body) {
        String guess = body.get("guess");
        GameSession session = gameService.getSession(sessionId);
        return session.makeGuess(guess);
    }

    @GetMapping("/state/{sessionId}")
    public GameSession getState(@PathVariable String sessionId) {
        return gameService.getSession(sessionId);
    }

}
