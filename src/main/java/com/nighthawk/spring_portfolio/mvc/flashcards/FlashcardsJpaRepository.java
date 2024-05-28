package com.nighthawk.spring_portfolio.mvc.flashcards;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface FlashcardsJpaRepository extends JpaRepository<Flashcards, Long> {
    List<Flashcards> findAllByOrderByQuestionAsc();
    List<Flashcards> findByQuestionIgnoreCase(String question);
    List<Flashcards> findByTopicIgnoreCase(String topic);
    @Query("SELECT DISTINCT f.topic FROM Flashcards f")
    List<String> findDistinctTopicsBy();
}
