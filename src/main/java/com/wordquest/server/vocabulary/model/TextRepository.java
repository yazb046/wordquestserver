package com.wordquest.server.vocabulary.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TextRepository extends JpaRepository<Text, Long> {

    String SQL =
            "SELECT * FROM public.\"T_TEXT\" " +
                    "WHERE contextword=:active_word " +
                    "OR text ILIKE '%' || :active_word || '%'";
    @Query(
            value = SQL,
            countQuery = "SELECT COUNT( * ) FROM (" + SQL + ") as BS",
            nativeQuery = true
    )
    Page<Text> getAllByActiveWords(@Param("active_word") String activeWord, Pageable pageable);

}