package com.wordquest.server.cards.controller;

import com.wordquest.server.cards.dto.Iterable;
import com.wordquest.server.cards.service.ThemeService;
import com.wordquest.server.cards.utils.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/goals")
public class GoalsController {
    @Autowired
    private ThemeService themeService;

    @GetMapping("/{userId}")
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
