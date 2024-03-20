package com.wordquest.server.controller;

import com.wordquest.server.dto.TextDTO;
import com.wordquest.server.service.CardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cards")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CardController {

    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @PostMapping("")
    public ResponseEntity<String> createCard(
            @RequestParam Long userId,
            @RequestBody TextDTO text) {
        try {
            String contextTitle = text.getContextTitle();
            String contextDescription = text.getText();
            Long textId = text.getId();
            cardService.save(userId, contextTitle, contextDescription, textId);
            return ResponseEntity.ok("User updated successfully");
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}