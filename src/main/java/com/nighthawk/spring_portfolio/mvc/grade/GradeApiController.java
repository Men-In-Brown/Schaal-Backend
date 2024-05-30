package com.nighthawk.spring_portfolio.mvc.grade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import com.nighthawk.hacks.GradeSorter;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/grade/")
public class GradeApiController {
    /*
    #### RESTful API ####
    Resource: https://spring.io/guides/gs/rest-service/
    */
    // Autowired enables Control to connect POJO Object through JPA
    @Autowired
    private GradeDetailsService gradeDetailsService;

    @Autowired
    private GradeJpaRepository repository;

    /*
    GET List of Grades
    */
    @GetMapping("/")
    public ResponseEntity<List<Grade>> getScore() {
        return new ResponseEntity<>(repository.findAllByOrderByNameAsc(), HttpStatus.OK);
    }

    /*
    GET individual Grade using ID
    */
    @GetMapping("/{id}")
    public ResponseEntity<Grade> getScore(@PathVariable long id) {
        Optional<Grade> optional = repository.findById(id);
        if (optional.isPresent()) {  // Good ID
            Grade grade = optional.get();  // value from findByID
            return new ResponseEntity<>(grade, HttpStatus.OK);  // OK HTTP response: status code, headers, and body
        }
        // Bad ID
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /*
    GET Sorted Grades
    */
    @GetMapping("/sort/{sortProperty}")
    public ResponseEntity<List<Grade>> getSortedGrades(@PathVariable String sortProperty) {
        List<Grade> result = GradeSorter.sortByProperty(repository.findAllByOrderByNameAsc(), sortProperty);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /*
    GET Grades by Email
    */
    @GetMapping("/email/{email}")
    public ResponseEntity<List<Grade>> gradeSearchByEmail(@PathVariable String email) {
        List<Grade> grades = gradeDetailsService.getAllByEmail(email);
        if (!grades.isEmpty()) {
            return new ResponseEntity<>(grades, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /*
    PUT Update Grade by ID or Email
    */
    @PutMapping("/update")
    public ResponseEntity<Object> updateScore(@RequestParam(value = "id", required = false) Long id,
                                              @RequestParam(value = "email", required = false) String email,
                                              @RequestParam("newEmail") String newEmail,
                                              @RequestParam("newAssignment") String newAssignment,
                                              @RequestParam("newScore") double newScore) {
        Optional<Grade> optional = Optional.empty();
        
        if (id != null) {
            optional = repository.findById(id);
        } else if (email != null) {
            Grade grade = repository.findByEmail(email);
            if (grade != null) {
                optional = Optional.of(grade);
            }
        }

        if (optional.isPresent()) {
            Grade grade = optional.get(); // read from database
            // Check if the email and assignment match the existing record
            if (grade.getEmail().equals(newEmail) && grade.getAssignment().equals(newAssignment)) {
                // Update the grade
                grade.setScore(newScore);
                repository.save(grade); // send request to update DB
                return new ResponseEntity<>("Grade updated successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Email and assignment do not match existing record", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("Grade not found", HttpStatus.NOT_FOUND);
        }
    }

    /*
    POST Create new Grade
    */
    @PostMapping("/post")
    public ResponseEntity<Object> postScore(@RequestParam("email") String email,
                                            @RequestParam("name") String name,
                                            @RequestParam("assignment") String assignment,
                                            @RequestParam("maxPoints") double maxPoints,
                                            @RequestParam("score") double score) {
        // A grade object WITHOUT ID will create a new record
        Grade grade = new Grade(email, name, assignment, maxPoints, score);
        repository.save(grade);
        return new ResponseEntity<>(name + " is created successfully", HttpStatus.CREATED);
    }

    /*
    POST Search Grades
    */
    @PostMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> gradeSearch(@RequestBody final Map<String, String> map) {
        // extract term from RequestEntity
        String term = map.get("term");

        // JPA query to filter on term
        List<Grade> list = repository.findByNameIgnoreCase(term);

        // return resulting list and status, error checking should be added
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
