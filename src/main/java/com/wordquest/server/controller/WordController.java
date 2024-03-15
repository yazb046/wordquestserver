package com.wordquest.server.controller;

import com.wordquest.server.entity.Word;
import com.wordquest.server.service.UserService;
import com.wordquest.server.service.WordService;
import org.springframework.data.domain.Page;
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
    public List<Word> getWords() {
        return wordService.getAll();
    }

    @GetMapping("/searchBy")
    public Page<Word> getWords(@RequestParam int userId,@RequestParam String filter, @RequestParam int pageNo, @RequestParam int pageSize,
                               @RequestParam String sortBy, @RequestParam String direction) {
        return wordService.getAllBy(userId,filter,pageNo, pageSize, sortBy, direction);
    }

    @GetMapping("/user")
    public List<Word> getWordsBy(@RequestParam Long userId) {
        return wordService.getAllBy(userId);
    }

    @PutMapping("/user")
    public Word addWordToUser(@RequestParam Long wordId, @RequestParam Long userId) {
        wordId = wordService.get(wordId).get().getId();
        userId = userService.get(userId).get().getId();
        return wordService.save(userId, wordId);
    }
}
