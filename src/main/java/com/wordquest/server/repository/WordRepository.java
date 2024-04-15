package com.wordquest.server.repository;

import com.wordquest.server.dto.WordDTO;
import com.wordquest.server.entity.UserWord;
import com.wordquest.server.entity.Word;
import jakarta.persistence.criteria.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public interface WordRepository extends JpaRepository<Word, Long>, JpaSpecificationExecutor<Word> {

    default Page<WordDTO> findAllByUserIdAndStatus(Long userId, String status, Pageable pageable) {
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

    default Page<WordDTO> findAllByUserIdAndLangLevel(Long userId, String langLevel, Pageable pageable) {
        Specification<Word> spec = (root, query, criteriaBuilder) -> {
            Join<Word, UserWord> userWords = root.join("userWords", JoinType.LEFT);
            query.multiselect(root, userWords.get("status"));
            if (!StringUtils.hasText(langLevel)) {
                return criteriaBuilder.equal(userWords.get("user").get("id"), userId);
            }
            return criteriaBuilder.and(
                    criteriaBuilder.equal(userWords.get("user").get("id"), userId),
                    criteriaBuilder.equal(userWords.get("lang_level"), langLevel)
            );
        };
        return findAll(spec, pageable).map(w -> WordDTO.builder()
                .id(w.getId())
                .word(w.getWord())
                .checked(w.getChecked())
                .langLevel(w.getLangLevel())
                .build());
    }

    default Page<WordDTO> findAllAndExcludeUserWords(Long userId, String status, Pageable pageable) {
        Specification<Word> spec = (root, query, criteriaBuilder) -> {
            Subquery<Long> subquery = query.subquery(Long.class);
            Root<UserWord> subRoot = subquery.from(UserWord.class);
            subquery.select(subRoot.get("word").get("id"));
            subquery.where(criteriaBuilder.equal(subRoot.get("user").get("id"), userId));

            query.multiselect(root, criteriaBuilder.literal(null));

            if (!StringUtils.hasText(status)) {
                return criteriaBuilder.not(criteriaBuilder.in(root.get("id")).value(subquery));
            }

            Subquery<Long> subqueryWithStatus = query.subquery(Long.class);
            Root<UserWord> subRootWithStatus = subqueryWithStatus.from(UserWord.class);
            subqueryWithStatus.select(subRootWithStatus.get("word").get("id"));
            subqueryWithStatus.where(
                    criteriaBuilder.and(
                            criteriaBuilder.equal(subRootWithStatus.get("user").get("id"), userId),
                            criteriaBuilder.equal(subRootWithStatus.get("status"), status)
                    )
            );

            return criteriaBuilder.and(
                    criteriaBuilder.not(criteriaBuilder.in(root.get("id")).value(subquery)),
                    criteriaBuilder.not(criteriaBuilder.in(root.get("id")).value(subqueryWithStatus))
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

    default Page<Word> findAllByUserId(Long userId, Pageable pageable) {
        Specification<Word> spec = (root, query, criteriaBuilder) -> {
            Join<Word, UserWord> userWords = root.join("userWords", JoinType.LEFT);
            return criteriaBuilder.equal(userWords.get("user").get("id"), userId);
        };
        return findAll(spec, pageable);
    }


    Page<Word> findAllByLangLevel(String langLevel, Pageable pageable);

    default Page<WordDTO> findAllByUserIAndLangLevelAndStatus(Long userId, String langLevel, String status, Pageable pageable) {
        Specification<Word> spec = (root, query, criteriaBuilder) -> {
            Join<Word, UserWord> userWords = root.join("userWords", JoinType.LEFT);
            query.multiselect(root, userWords.get("status"));
            if (!StringUtils.hasText(status)) {
                return criteriaBuilder.equal(userWords.get("user").get("id"), userId);
            }
            return criteriaBuilder.and(
                    criteriaBuilder.equal(userWords.get("user").get("id"), userId),
                    criteriaBuilder.equal(userWords.get("status"), status),
                    criteriaBuilder.equal(userWords.get("lang_level"), langLevel)
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

    List<Word> findAllByWordContaining(String word);

    default List<Word> findAllWordsBy(Set<Long> wordIds) {
        Specification<Word> spec = Specification.where((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(root.get("id").in(wordIds));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });

        return findAll(spec);
    }
}
