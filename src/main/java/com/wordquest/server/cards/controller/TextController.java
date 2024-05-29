package com.wordquest.server.cards.controller;

import com.wordquest.server.cards.entity.Text;
import com.wordquest.server.cards.service.TextService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/texts")
public class TextController {
    private final TextService textService;

    public TextController(TextService textService) {
        this.textService = textService;
    }

    @GetMapping("/searchBy")
    public Page<List<Text>> getAllByUserWords(@RequestParam long userId, @RequestParam String word,
                                              @RequestParam String filter, @RequestParam int pageNo,
                                              @RequestParam int pageSize, @RequestParam String sortBy,
                                              @RequestParam String direction) {
        return textService.getAllByActiveWordsAndByUserId(userId, word, filter, pageNo,
                pageSize, sortBy, direction);
    }

}