package com.nighthawk.spring_portfolio.mvc.betterhallpass;

import com.nighthawk.spring_portfolio.mvc.person.Person;
import com.nighthawk.spring_portfolio.mvc.person.PersonJpaRepository;
import com.nighthawk.spring_portfolio.mvc.person.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hall-pass")
public class HallPassController {
    @Autowired
    private HallPassService hallPassService;

    @Autowired
    private PersonJpaRepository studentRepository;

    @PostMapping("/add")
    public ResponseEntity<?> addToQueue(@RequestParam Long studentId) {
        Person student = studentRepository.findById(studentId).orElse(null);
        if (student != null) {
            hallPassService.addToQueue((Student) student);
            return ResponseEntity.ok("Student added to the queue");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/next")
    public ResponseEntity<?> getNextInQueue() {
        Student nextStudent = hallPassService.peekNextInQueue();
        if (nextStudent != null) {
            return ResponseEntity.ok(nextStudent);
        } else {
            return ResponseEntity.ok("Queue is empty");
        }
    }

    @PostMapping("/remove")
    public ResponseEntity<?> removeNextInQueue() {
        Student nextStudent = hallPassService.removeFromQueue();
        if (nextStudent != null) {
            return ResponseEntity.ok("Removed student: " + nextStudent.getName());
        } else {
            return ResponseEntity.ok("Queue is empty");
        }
    }
}