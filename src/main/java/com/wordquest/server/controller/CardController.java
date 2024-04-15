package com.wordquest.server.controller;

import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.wordquest.server.dto.CardDTO;
import com.wordquest.server.dto.TextDTO;
import com.wordquest.server.entity.Card;
import com.wordquest.server.service.CardService;
import com.wordquest.server.utils.Helper;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cards")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CardController {

    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }


    @GetMapping("")
    public Page<CardDTO> getCardsBy(@RequestParam Long userId,
                                       @RequestParam int pageNo,
                                       @RequestParam int pageSize,
                                       @RequestParam String sortBy,
                                       @RequestParam String direction){
        return cardService.getCardsBy(userId, Helper.buildPageable(pageNo, pageSize, direction, "id"));
    }

    @PostMapping("")
    public ResponseEntity<String> createCard(
            @RequestParam Long userId,
            @RequestParam Long wordId,
            @RequestBody CardDTO card) {
        try {
            cardService.save(userId, wordId,card);
            return ResponseEntity.ok("The card is created successfully");
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/update")
    public ResponseEntity<String> update(
            @RequestBody CardDTO card) {
        try {
            cardService.save(card);
            return ResponseEntity.ok("The card is updated successfully");
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}