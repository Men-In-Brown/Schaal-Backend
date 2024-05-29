package com.nighthawk.spring_portfolio.mvc.flashcards;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/flashcards")
public class FlashcardsApiController {

    @Autowired
    private FlashcardsJpaRepository repository;

    @GetMapping("/")
    public ResponseEntity<List<Flashcards>> getFlashcards() {
        return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Object> postFlashcard(@RequestBody Flashcards flashcard) {
        repository.save(flashcard);
        return new ResponseEntity<>(flashcard + " is created successfully", HttpStatus.CREATED);
    }

    @GetMapping("/topics")
    public ResponseEntity<List<String>> getTopics() {
        List<String> topics = repository.findDistinctTopicsBy();
        return new ResponseEntity<>(topics, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Flashcards> updateFlashcard(@PathVariable Long id, @RequestBody Flashcards flashcardDetails) {
        Flashcards flashcard = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Flashcard not found for this id :: " + id));

        flashcard.setTopic(flashcardDetails.getTopic());
        flashcard.setQuestion(flashcardDetails.getQuestion());
        flashcard.setAnswer(flashcardDetails.getAnswer());
        final Flashcards updatedFlashcard = repository.save(flashcard);
        return ResponseEntity.ok(updatedFlashcard);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteFlashcard(@PathVariable Long id) {
        Flashcards flashcard = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Flashcard not found for this id :: " + id));

        repository.delete(flashcard);
        return ResponseEntity.noContent().build();
    }
}