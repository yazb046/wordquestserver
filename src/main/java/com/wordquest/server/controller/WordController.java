package com.wordquest.server.controller;

import com.wordquest.server.dto.WordDTO;
import com.wordquest.server.entity.Word;
import com.wordquest.server.service.WordService;
import com.wordquest.server.utils.Helper;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/words")
@CrossOrigin(origins = "*", maxAge = 3600)
public class WordController {

    private final WordService wordService;

    public WordController(WordService wordService) {
        this.wordService = wordService;
    }

    @GetMapping("")
    public Page<Word> getWords(@RequestParam int pageNo, @RequestParam int pageSize,
                               @RequestParam String sortBy, @RequestParam String direction) {

        return wordService.getAll(Helper.buildPageable(pageNo, pageSize, direction, sortBy));
    }

    @GetMapping("/searchBy")
    public ResponseEntity<Page<WordDTO>> getWordsBy
            (@RequestParam(value = "userId", required = false) Long userId,
             @RequestParam(value = "status", required = false) String status,
             @RequestParam(value = "langLevel", required = false) String langLevel,
             @RequestParam int pageNo,
             @RequestParam int pageSize,
             @RequestParam String sortBy,
             @RequestParam String direction) {

        if (StringUtils.hasText(status) && userId == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.of(
                wordService.getAllBy(userId, status, langLevel.toUpperCase(), Helper.buildPageable(pageNo, pageSize, direction, sortBy)
                ));
    }

    @GetMapping("/find")
    public ResponseEntity<List<Word>> getWordsBy(
            @RequestParam String wordLetters) {
        return ResponseEntity.of(
                wordService.getAllBy(wordLetters)
        );
    }

    @PostMapping("")
    public void saveWordsBy(@RequestParam Long userId, @RequestBody List<WordDTO> words){
        wordService.saveWordsBy(userId, words);
    }

}
