package com.nighthawk.spring_portfolio.mvc.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.nighthawk.hacks.classDataStruct.Person;
import com.nighthawk.spring_portfolio.mvc.person.PersonRequest;
import com.nighthawk.spring_portfolio.mvc.person.PersonRole;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/Student")
public class StudentApiController {

    @Autowired
    private StudentJpaRepository repository;

    @GetMapping("/")
    public ResponseEntity<List<Student>> getPeople() {
        return new ResponseEntity<>(repository.findAllByOrderByNameAsc(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable long id) {
        Optional<Student> optional = repository.findById(id);
        if (optional.isPresent()) {
            Student Student = optional.get();
            return new ResponseEntity<>(Student, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Student> deleteStudent(@PathVariable long id) {
        Optional<Student> optional = repository.findById(id);
        if (optional.isPresent()) {
            Student Student = optional.get();
            repository.deleteById(id);
            return new ResponseEntity<>(Student, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/post")
    public ResponseEntity<Object> postPerson(@RequestBody PersonRequest request) {
        Date dob = null; // Initialize dob to null
    
        try {
            dob = new SimpleDateFormat("MM-dd-yyyy").parse(request.getDob());
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Invalid date format. Please use MM-dd-yyyy format.", HttpStatus.BAD_REQUEST);
        }
    
        // Check if dob is still null after the try-catch block
        if (dob == null) {
            return new ResponseEntity<>("Invalid date format. Please use MM-dd-yyyy format.", HttpStatus.BAD_REQUEST);
        }
    
        // Find or create the role
        PersonRole role = repository.findByName(request.getRole());
        if (role == null) {
            // Role doesn't exist, create a new one
            role = new PersonRole(request.getRole());
            repository.save(role);
        }
    
        // Create and save Person entity
        Student student = new Student(null, request.getEmail(), request.getPassword(), request.getName(), dob, null, null, 0, null, null, null);
        student.getRoles().add(role); // Add the role to the person
        repository.save(student);
    
        return new ResponseEntity<>(request.getEmail() + " is created successfully", HttpStatus.CREATED);
    }    
    @PostMapping("/search")
    public ResponseEntity<Object> StudentSearch(@RequestBody final String term) {
        List<Student> list = repository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(term, term);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping(value = "/setStats", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Student> StudentStats(@RequestBody final Map<String,Object> stat_map) {
        // find ID
        long id=Long.parseLong((String)stat_map.get("id"));  
        Optional<Student> optional = repository.findById((id));
        if (optional.isPresent()) {  // Good ID
            Student Student = optional.get();  // value from findByID

            // Extract Attributes from JSON
            Map<String, Object> attributeMap = new HashMap<>();
            for (Map.Entry<String,Object> entry : stat_map.entrySet())  {
                // Add all attribute other thaN "date" to the "attribute_map"
                if (!entry.getKey().equals("date") && !entry.getKey().equals("id"))
                    attributeMap.put(entry.getKey(), entry.getValue());
            }

            // Set Date and Attributes to SQL HashMap
            Map<String, Map<String, Object>> date_map = new HashMap<>();
            date_map.put( (String) stat_map.get("date"), attributeMap );
            Student.setStats(date_map);  // BUG, needs to be customized to replace if existing or append if new
            repository.save(Student);  // conclude by writing the stats updates

            // return Student with update Stats
            return new ResponseEntity<>(Student, HttpStatus.OK);
        }
        // return Bad ID
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST); 
    }

    @GetMapping("/compareClassesWithPopulation/{StudentId}")
    public ResponseEntity<List<String>> compareClassesWithPopulation(@PathVariable Long StudentId) {
        Optional<Student> optionalStudent = repository.findById(StudentId);
    
        if (optionalStudent.isPresent()) {
            Student Student = optionalStudent.get();
            List<Student> allStudents = repository.findAll();
            List<String> responseMessages = new ArrayList<>();
    
            for (Student otherStudent : allStudents) {
                if (!otherStudent.getId().equals(StudentId)) {
                    List<String> similarClasses = findSimilarClasses(Student, otherStudent);
                    if (!similarClasses.isEmpty()) {
                        String message = String.format("You have classes with %s. Here are the classes you have together: %s",
                                                       otherStudent.getName(), similarClasses.toString());
                        responseMessages.add(message);
                    }
                }
            }
    
            if (responseMessages.isEmpty()) {
                return ResponseEntity.ok(Collections.singletonList("No similar classes found with any other user."));
            } else {
                return ResponseEntity.ok(responseMessages);
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    private List<String> findSimilarClasses(Student Student1, Student Student2) {
        Map<String, Map<String, Object>> stats1 = Student1.getStats();
        Map<String, Map<String, Object>> stats2 = Student2.getStats();

        List<String> similarClasses = new ArrayList<>();

        for (String date : stats1.keySet()) {
            if (stats2.containsKey(date)) {
                Map<String, Object> attributes1 = stats1.get(date);
                Map<String, Object> attributes2 = stats2.get(date);

                for (String period : attributes1.keySet()) {
                    if (attributes2.containsKey(period) && attributes1.get(period).equals(attributes2.get(period))) {
                        similarClasses.add((String) attributes1.get(period));
                    }
                }
            }
        }

        return similarClasses;
    }

} 