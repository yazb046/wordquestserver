package com.wordquest.server.repository;

import com.wordquest.server.entity.TextEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TextRepository extends JpaRepository<TextEntity, Long> {

    @Query(
            value = "WITH word_list AS ( " +
                    "select word from \"T_WORD\" where id in (select word_id from \"T_USER_WORD\" where user_id = 1) " +
                    ") " +
                    "SELECT id, text, lang_level, COUNT(*) AS word_occurrence " +
                    "FROM \"T_TEXT\" " +
                    "JOIN word_list ON text ILIKE '%' || word || '%' " +
                    "GROUP BY id, text, lang_level " +
                    "ORDER BY word_occurrence DESC;",
            nativeQuery = true
    )
    List<TextEntity> getAllByActiveWordsAndByUserId(Long userId);
}