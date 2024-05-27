package com.nighthawk.spring_portfolio.mvc.assignment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlashcardJpaRepository extends JpaRepository<Flashcard, Long> {
}
