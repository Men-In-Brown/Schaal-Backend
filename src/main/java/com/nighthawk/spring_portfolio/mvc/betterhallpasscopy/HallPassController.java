package com.nighthawk.spring_portfolio.mvc.betterhallpasscopy;

import com.nighthawk.spring_portfolio.mvc.person.Person;
import com.nighthawk.spring_portfolio.mvc.person.PersonJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hall-pass")
public class HallPassController {
    @Autowired
    private HallPassService hallPassService;

    @PostMapping("/add")
    public ResponseEntity<?> addToQueue(@RequestParam Long studentId) {
        if (hallPassService.addToQueue(studentId)) {
            return ResponseEntity.ok("Student added to the queue");
        } else {
            return ResponseEntity.badRequest().build(); // or a more specific error message
        }
    }

    @GetMapping("/next")
    public ResponseEntity<?> getNextInQueue() {
        Long nextStudent = hallPassService.peekNextInQueue();
        if (nextStudent != null) {
            return ResponseEntity.ok(nextStudent);
        } else {
            return ResponseEntity.ok("Queue is empty");
        }
    }

    @PostMapping("/remove")
    public ResponseEntity<?> removeNextInQueue() {
        Long nextStudent = hallPassService.removeFromQueue();
        if (nextStudent != null) {
            return ResponseEntity.ok("Removed student");
        } else {
            return ResponseEntity.ok("Queue is empty");
        }
    }
}