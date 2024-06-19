package com.wordquest.server.vocabulary;

import com.wordquest.server.vocabulary.DTO.Iterable;
import com.wordquest.server.vocabulary.Service.TextService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/texts")
public class TextController {
    private final TextService textService;

    public TextController(TextService textService) {
        this.textService = textService;
    }

    @GetMapping("/searchBy")
    public Page<Iterable> getAllByUserWords(@RequestParam long userId,
                                            @RequestParam String word,
                                            @RequestParam String filter,
                                            @RequestParam int pageNo,
                                            @RequestParam int pageSize,
                                            @RequestParam String sortBy,
                                            @RequestParam String direction) {
        return textService.getAllByActiveWordsAndByUserId(
                userId, word, filter, pageNo, pageSize, sortBy, direction
        );
    }

}