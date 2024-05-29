package com.wordquest.server.cards.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

public class Helper {
    public static Pageable buildPageable(int pageNo, int pageSize, String direction, String sortBy) {
        Sort.Direction sortDirection = null;
        if (StringUtils.hasText(direction)) {
            sortDirection = Sort.Direction.ASC;
            if ("desc".equalsIgnoreCase(direction)) {
                sortDirection = Sort.Direction.DESC;
            }
        }

        if (!StringUtils.hasText(sortBy) && sortDirection != null) {
            return PageRequest.of(pageNo, pageSize, sortDirection);
        }

        if (!StringUtils.hasText(sortBy) && sortDirection == null) {
            return PageRequest.of(pageNo, pageSize);
        }
        return PageRequest.of(pageNo, pageSize, sortDirection, sortBy);
    }
}