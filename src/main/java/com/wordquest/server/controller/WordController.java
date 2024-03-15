package com.wordquest.server.controller;

import com.wordquest.server.dto.WordDTO;
import com.wordquest.server.entity.Word;
import com.wordquest.server.service.WordService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/words")
@CrossOrigin(origins = "*", maxAge = 3600)
public class WordController {

    private final WordService wordService;

    public WordController(WordService wordService) {
        this.wordService = wordService;
    }

    @GetMapping("")
    public List<Word> getWords() {
        return wordService.getAll();
    }

    @GetMapping("/searchBy")
    public Page<WordDTO> getWords(@RequestParam int userId, @RequestParam String filter, @RequestParam int pageNo, @RequestParam int pageSize,
                                  @RequestParam String sortBy, @RequestParam String direction) {
        return wordService.getAllBy(userId,filter,pageNo, pageSize, sortBy, direction);
    }

}
