package com.wordquest.server.cards.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface StepRepository extends JpaRepository<Step, Long>, JpaSpecificationExecutor<Step> {

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
            "SELECT c FROM Step c WHERE goalId = :goalId " +
                    "AND c.pkid.version = ( SELECT MAX(c2.pkid.version) " +
                    "FROM Step c2 " +
                    "WHERE c2.pkid.id = c.pkid.id) " +
                    "ORDER BY c.pkid.id DESC "
    )
    Page<Step> findAllLatestByThemeId(@Param("goalId") Long goalId, Pageable pageable);

    @Query(
            "SELECT c FROM Step c WHERE goalId = :goalId " +
                    "AND c.pkid.version = (SELECT MAX(c2.pkid.version) " +
                    "FROM Step c2 WHERE c2.pkid.id = c.pkid.id) " +
                    "ORDER BY c.index ASC, c.pkid.id ASC"
    )
    Page<Step> findCardsByThemeIdOrdered(@Param("goalId") Long goalId, Pageable pageable);

    @Query("SELECT MAX(pkid.id) FROM Step c")
    Optional<Long> findLastId();

    @Query(
            "SELECT c FROM Step c WHERE c.pkid.id= :id AND " +
                    "c.pkid.version = (SELECT MAX(c2.pkid.version) " +
                    "FROM Step c2 " +
                    "WHERE c2.pkid.id = :id) "
    )
    Optional<Step> findLatestById(@Param("id") Long id);

    @Query(
            "SELECT c FROM Step c WHERE c.pkid.id IN :ids AND " +
                    "c.pkid.version = (SELECT MAX(c2.pkid.version) " +
                    "FROM Step c2 " +
                    "WHERE c2.pkid.id = c.pkid.id) "
    )
    Optional<List<Step>> findAllByIdsWithLatestVersions(@Param("ids") List<Long> ids);
}