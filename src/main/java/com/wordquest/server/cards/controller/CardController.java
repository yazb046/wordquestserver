package com.wordquest.server.cards.controller;

import com.wordquest.server.cards.dto.CardDTO;
import com.wordquest.server.cards.dto.Iterable;
import com.wordquest.server.cards.dto.ThemeDTO;
import com.wordquest.server.cards.model.ThemeType;
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

    @GetMapping("/order/{themeId}")
    public Page<CardDTO> getCardsOrdered(
            @PathVariable Long themeId,
            @RequestParam int pageNo,
            @RequestParam int pageSize
    ) {
        return cardService.getAllCardsOrdered(themeId, Helper.buildPageable(pageNo, pageSize, "", ""));
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

    @PostMapping("/theme/{userId}")
    public ResponseEntity<String> saveTheme(
            @PathVariable Long userId,
            @RequestBody ThemeDTO body) {
        try {
            themeService.save(userId, body);
            return ResponseEntity.ok("The theme is created");
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/themeTypes")
    public ResponseEntity<List<ThemeType>> getThemeTypes() {
        try {
            return ResponseEntity.ok(themeService.getAllThemeTypes());
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/theme/{userId}")
    public Page<Iterable> getThemes(
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