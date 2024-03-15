package com.wordquest.server.repository;

import com.wordquest.server.entity.UserWord;
import com.wordquest.server.entity.Word;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface WordRepository extends JpaRepository<Word, Long>, JpaSpecificationExecutor<Word> {

    default Page<Word> findAllBy(Long userId, String status, Pageable pageable){
        Specification<Word> spec = (root, query, criteriaBuilder) -> {
            Join<Word, UserWord> userWords = root.join("userWords", JoinType.LEFT);
            return criteriaBuilder.and(
                    criteriaBuilder.equal(userWords.get("user").get("id"), userId),
                    criteriaBuilder.equal(userWords.get("status"), status)
            );
        };
        return findAll(spec, pageable);
    }

    default List<Word> findAllBy(Long userId){
        Specification<Word> spec = (root, query, criteriaBuilder) -> {
            Join<Word, UserWord> userWords = root.join("userWords", JoinType.LEFT);
            return criteriaBuilder.equal(userWords.get("user").get("id"), userId);
        };
        return findAll(spec);
    }
    default Page<Word> findAllBy(Long userId, Pageable pageable){
        Specification<Word> spec = (root, query, criteriaBuilder) -> {
            Join<Word, UserWord> userWords = root.join("userWords", JoinType.LEFT);
            return criteriaBuilder.equal(userWords.get("user").get("id"), userId);
        };
        return findAll(spec, pageable);
    }
}
