package com.wordquest.server.cards.controller;

import com.wordquest.server.cards.dto.CardDTO;
import com.wordquest.server.cards.dto.ThemeDTO;
import com.wordquest.server.cards.service.CardService;
import com.wordquest.server.cards.service.ThemeService;
import com.wordquest.server.cards.utils.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cards")
public class CardController {

    @Autowired
    private CardService cardService;
    @Autowired
    private ThemeService themeService;

    @GetMapping("/{themeId}")
    public Page<CardDTO> getCardsBy(
            @PathVariable Long themeId,
            @RequestParam int pageNo,
            @RequestParam int pageSize,
            @RequestParam String sortBy,
            @RequestParam String direction
    ) {
        return cardService.getAllCardsOrderedBy(themeId, Helper.buildPageable(pageNo, pageSize, direction, "id"));
    }

    @PostMapping("/{themeId}")
    public CardDTO createCard(
            @PathVariable Long themeId,
            @RequestBody CardDTO card
    ) {
        return cardService.saveCard(themeId, card);
    }

    @PostMapping("/order/{themeId}")
    public ResponseEntity saveCardOrder(
            @PathVariable Long themeId,
            @RequestBody List<Long> ids
    ) {

        cardService.saveCardOrder(themeId, ids);
        return ResponseEntity.ok().build();
    }

    //    @PostMapping("")
//    public ResponseEntity<String> createCard(
//            @RequestParam Long userId,
//            @RequestParam Long wordId,
//            @RequestBody CardDTO card) {
//        try {
//            cardService.save(userId, wordId,card);
//            return ResponseEntity.ok("The card is created successfully");
//        } catch (RuntimeException ex) {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//    @PostMapping("/update")
//    public ResponseEntity<String> update(
//            @RequestBody CardDTO card) {
//        try {
//            cardService.save(card);
//            return ResponseEntity.ok("The card is updated successfully");
//        } catch (RuntimeException ex) {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
    @PostMapping("/theme/{userId}")
    public ResponseEntity<String> saveTheme(
            @PathVariable Long userId,
            @RequestBody ThemeDTO body) {
        try {
            themeService.save(userId, body.getTitle(), body.getDescription());
            return ResponseEntity.ok("The theme is created");
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/theme/{userId}")
    public Page<ThemeDTO> getThemes(
            @PathVariable Long userId,
            @RequestParam int pageNo,
            @RequestParam int pageSize,
            @RequestParam String sortBy,
            @RequestParam String direction
    ) {
        return themeService
                .findAllBy(userId, Helper.buildPageable(pageNo, pageSize, direction, sortBy));
    }
}