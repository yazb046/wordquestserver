package com.wordquest.server.repository;

import com.wordquest.server.entity.Text;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TextRepository extends JpaRepository<Text, Long> {

    @Query(
            value = "WITH word_list AS ( " +
                    "select word from \"T_WORD\" where id in (select word_id from \"T_USER_WORD\" where user_id =:user_id) " +
                    ") " +
                    "SELECT id, text, lang_level, checked, source, LINK_TO_AUDIO, COUNT(*) AS user_words_occurrence, " +
                    "CASE WHEN text ILIKE '%' || :active_word || '%' THEN true ELSE false END AS contains_active_word " +
                    "FROM \"T_TEXT\" " +
                    "JOIN word_list ON text ILIKE '%' || word || '%' " +
                    "GROUP BY id, text, lang_level, checked, source, LINK_TO_AUDIO " +
                    "ORDER BY contains_active_word DESC, user_words_occurrence DESC;",
            nativeQuery = true
    )
    List<Text> getAllByActiveWordsAndByUserId(@Param("user_id")Long userId, @Param("active_word") String activeWord);
}