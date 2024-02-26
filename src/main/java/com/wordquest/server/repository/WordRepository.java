package com.wordquest.server.repository;

import com.wordquest.server.entity.WordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WordRepository extends JpaRepository<WordEntity, Long> {

    @Query(
            value = "SELECT * FROM \"T_WORD\" WHERE id in (SELECT WORD_ID FROM \"T_USER_WORD\" WHERE USER_ID=:user_id)",
            nativeQuery = true
    )
    public List<WordEntity> findAllByUserId(@Param("user_id") Long userId);
}
