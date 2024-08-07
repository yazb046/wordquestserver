package com.wordquest.server.cards.controller;

import com.wordquest.server.cards.dto.CardDTO;
import com.wordquest.server.cards.service.CardService;
import com.wordquest.server.cards.utils.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/steps")
public class StepsController {

    @Autowired
    private CardService cardService;

    @GetMapping
    public Page<CardDTO> getCardsOrdered(
            @RequestParam int pageNo,
            @RequestParam int pageSize,
            @RequestParam long goalId
    ) {
        return cardService.getAllCardsOrdered(goalId, Helper.buildPageable(pageNo, pageSize, "", ""));
    }

    @PostMapping("/{goalId}")
    public CardDTO createCard(
            @PathVariable Long goalId,
            @RequestBody CardDTO card
    ) {
        return cardService.saveCard(goalId, card);
    }
}
