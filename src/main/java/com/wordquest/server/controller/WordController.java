package com.wordquest.server.controller;

import com.wordquest.server.entity.WordEntity;
import com.wordquest.server.service.UserService;
import com.wordquest.server.service.WordService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/words")
@CrossOrigin(origins = "*", maxAge = 3600)
public class WordController {

    private final WordService wordService;
    private final UserService userService;

    public WordController(WordService wordService, UserService userService) {
        this.wordService = wordService;
        this.userService = userService;
    }

    @GetMapping("")
    public List<WordEntity> listWords() {
        return wordService.getAll();
    }

    @PutMapping("/update")
    public WordEntity addWordToUser(@RequestParam Long wordId, @RequestParam Long userId) {
        wordId = wordService.get(wordId).get().getId();
        userId = userService.get(userId).get().getId();
        return wordService.addWordToUser(userId, wordId);
    }

}
