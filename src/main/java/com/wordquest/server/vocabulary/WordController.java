package com.wordquest.server.vocabulary;

import com.wordquest.server.cards.utils.Helper;
import com.wordquest.server.vocabulary.DTO.Iterable;
import com.wordquest.server.vocabulary.Service.WordService;
import com.wordquest.server.vocabulary.model.Dictionary;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/words")
public class WordController {

    private final WordService wordService;

    public WordController(WordService wordService) {
        this.wordService = wordService;
    }


    @GetMapping("/searchBy")
    public ResponseEntity<Page<Iterable>> getWordsBy
            (@RequestParam(value = "id", required = false) Long userId,
             @RequestParam(value = "status", required = false) String status,
             @RequestParam(value = "langLevel", required = false) String langLevel,
             @RequestParam(value = "dict") Long dict,
             @RequestParam int pageNo,
             @RequestParam int pageSize,
             @RequestParam String sortBy,
             @RequestParam String direction) {

        if (StringUtils.hasText(status) && userId == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.of(
                wordService.getAllUserWords(userId, dict, Helper.buildPageable(pageNo, pageSize, direction, sortBy)
                ));
    }

    @GetMapping("/byDict")
    public ResponseEntity<Page<Iterable>> getDict(
            @RequestParam(value = "langLevel", required = false) String langLevel,
            @RequestParam(value = "dict") Long dictId,
            @RequestParam int pageNo,
            @RequestParam int pageSize,
            @RequestParam String sortBy,
            @RequestParam String direction) {


        return ResponseEntity.of(
                Optional.of(wordService.getAllByDict(dictId, langLevel.toUpperCase(), Helper.buildPageable(pageNo, pageSize, direction, sortBy)
                )));
    }

    @GetMapping("/dicts")
    public ResponseEntity<List<Dictionary>> getDictionaries() {
        return ResponseEntity.of(
                Optional.of(wordService.getDictionaries())
        );
    }

    @PostMapping("")
    public void saveWordsBy(@RequestParam Long id, @RequestBody Set<Long> wordIds) {
        wordService.saveWordsBy(id, wordIds);
    }

}
