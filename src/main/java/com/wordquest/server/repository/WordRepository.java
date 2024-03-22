package com.wordquest.server.repository;

import com.wordquest.server.dto.WordDTO;
import com.wordquest.server.entity.UserWord;
import com.wordquest.server.entity.Word;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.util.StringUtils;

import java.util.List;

public interface WordRepository extends JpaRepository<Word, Long>, JpaSpecificationExecutor<Word> {

    default Page<WordDTO> findAllByStatus(Long userId, String status, Pageable pageable) {
        Specification<Word> spec = (root, query, criteriaBuilder) -> {
            Join<Word, UserWord> userWords = root.join("userWords", JoinType.LEFT);
            query.multiselect(root, userWords.get("status"));
            if (!StringUtils.hasText(status)) {
                return criteriaBuilder.equal(userWords.get("user").get("id"), userId);
            }
            return criteriaBuilder.and(
                    criteriaBuilder.equal(userWords.get("user").get("id"), userId),
                    criteriaBuilder.equal(userWords.get("status"), status)
            );
        };
        return findAll(spec, pageable).map(w -> WordDTO.builder()
                .id(w.getId())
                .word(w.getWord())
                .checked(w.getChecked())
                .langLevel(w.getLangLevel())
                .status(w.getUserWords()
                        .stream()
                        .filter(e -> e.getId().getWordId() == w.getId() && e.getId().getUserId() == userId)
                        .findAny().get().getStatus())
                .build());
    }

    default List<Word> findAllBy(Long userId) {
        Specification<Word> spec = (root, query, criteriaBuilder) -> {
            Join<Word, UserWord> userWords = root.join("userWords", JoinType.LEFT);
            return criteriaBuilder.equal(userWords.get("user").get("id"), userId);
        };
        return findAll(spec);
    }

    default Page<Word> findAllBy(Long userId, Pageable pageable) {
        Specification<Word> spec = (root, query, criteriaBuilder) -> {
            Join<Word, UserWord> userWords = root.join("userWords", JoinType.LEFT);
            return criteriaBuilder.equal(userWords.get("user").get("id"), userId);
        };
        return findAll(spec, pageable);
    }
}
