package com.weiyi.demoWordle.rest;


import com.weiyi.demoWordle.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// separate from the frontend
//@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/wordle")
public class WordController {

    private WordService wordService;

    @Autowired
    public WordController(WordService wordService) {
        this.wordService = wordService;
    }

    @GetMapping("/words")
    public List<String> getWord() {
        return wordService.getAllWords();
    }

    @PostMapping("/check")
    public boolean checkWord() {
        return false;
    }
}
