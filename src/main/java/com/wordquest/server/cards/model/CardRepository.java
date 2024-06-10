package com.wordquest.server.cards.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface CardRepository extends JpaRepository<Card, Long>, JpaSpecificationExecutor<Card> {

//    default Page<Card> findAllLatestByThemeId(Long themeId, Pageable pageable) {
//
//        Specification<Card> spec = (root, query, criteriaBuilder) -> {
//            Subquery<Integer> maxVersionSubquery = query.subquery(Integer.class);
//            Root<Card> subRoot = maxVersionSubquery.from(Card.class);
//            maxVersionSubquery.select(criteriaBuilder.max(subRoot.get("pkid").get("version")))
//                    .where(
//                            criteriaBuilder.equal(subRoot.get("themeId"), themeId),
//                            criteriaBuilder.equal(subRoot.get("pkid").get("id"), root.get("pkid").get("id"))
//                    );
//
//            return criteriaBuilder.and(
//                    criteriaBuilder.equal(root.get("themeId"), themeId),
//                    criteriaBuilder.equal(root.get("pkid").get("version"), maxVersionSubquery)
//            );
//        };
//
//        return findAll(spec, pageable);
//    }

    @Query(
            "SELECT c FROM Card c WHERE themeId = :themeId " +
                    "AND c.pkid.version = ( SELECT MAX(c2.pkid.version) " +
                    "FROM Card c2 " +
                    "WHERE c2.pkid.id = c.pkid.id) " +
                    "ORDER BY c.pkid.id DESC "
    )
    Page<Card> findAllLatestByThemeId(@Param("themeId") Long themeId, Pageable pageable);

    @Query("SELECT MAX(pkid.id) FROM Card c")
    Optional<Long> findLastId();

    @Query(
            "SELECT c FROM Card c WHERE c.pkid.id= :id AND " +
                    "c.pkid.version = (SELECT MAX(c2.pkid.version) " +
                    "FROM Card c2 " +
                    "WHERE c2.pkid.id = :id) "
    )
    Optional<Card> findLatestById(@Param("id") Long id);
}