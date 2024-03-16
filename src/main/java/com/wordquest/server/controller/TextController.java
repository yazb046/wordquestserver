package com.wordquest.server.controller;

import com.wordquest.server.entity.Text;
import com.wordquest.server.service.TextService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/texts")
@CrossOrigin(origins = "*", maxAge = 3600)
public class TextController {
    private final TextService textService;

    public TextController(TextService textService) {
        this.textService = textService;
    }

    @GetMapping("/user")
    public List<Text> getAllByUserWords(@RequestParam Long userId, @RequestParam Long wordId) {
        return textService.getAllByActiveWordsAndByUserId(userId, wordId);
    }
}