package com.wordquest.server.controller;

import com.wordquest.server.dto.TextDTO;
import com.wordquest.server.entity.Text;
import com.wordquest.server.service.TextService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/searchBy")
    public Page<List<Text>> getAllByUserWords(@RequestParam long userId, @RequestParam String word,
                                              @RequestParam String filter, @RequestParam int pageNo,
                                              @RequestParam int pageSize, @RequestParam String sortBy,
                                              @RequestParam String direction) {
        return textService.getAllByActiveWordsAndByUserId(userId, word, filter, pageNo,
                pageSize, sortBy, direction);
    }

}