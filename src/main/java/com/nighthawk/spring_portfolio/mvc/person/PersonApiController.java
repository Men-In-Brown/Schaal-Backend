package com.nighthawk.spring_portfolio.mvc.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/person")
public class PersonApiController {

    @Autowired
    private PersonJpaRepository repository;

    @GetMapping("/")
    public ResponseEntity<List<Person>> getPeople() {
        return new ResponseEntity<>(repository.findAllByOrderByNameAsc(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> getPerson(@PathVariable long id) {
        Optional<Person> optional = repository.findById(id);
        if (optional.isPresent()) {
            Person person = optional.get();
            return new ResponseEntity<>(person, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Person> deletePerson(@PathVariable long id) {
        Optional<Person> optional = repository.findById(id);
        if (optional.isPresent()) {
            Person person = optional.get();
            repository.deleteById(id);
            return new ResponseEntity<>(person, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/post")
    public ResponseEntity<Object> postPerson(@RequestParam("email") String email,
                                            @RequestParam("password") String password,
                                            @RequestParam("name") String name,
                                            @RequestParam("dob") String dobString,
                                            @RequestParam("role") String roleName) {
        Date dob = null; // Initialize dob to null
    
        try {
            dob = new SimpleDateFormat("MM-dd-yyyy").parse(dobString);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Invalid date format. Please use MM-dd-yyyy format.", HttpStatus.BAD_REQUEST);
        }
    
        // Check if dob is still null after the try-catch block
        if (dob == null) {
            return new ResponseEntity<>("Invalid date format. Please use MM-dd-yyyy format.", HttpStatus.BAD_REQUEST);
        }
    
        // Find or create the role
        PersonRole role = repository.findByName(roleName);
        if (role == null) {
            // Role doesn't exist, create a new one
            role = new PersonRole(roleName);
            repository.save(role);
        }
    
        // Create and save Person entity
        Person person = new Person(email, password, name, dob);
        person.getRoles().add(role); // Add the role to the person
        repository.save(person);
    
        return new ResponseEntity<>(email + " is created successfully", HttpStatus.CREATED);
    }
    



    @PostMapping("/search")
    public ResponseEntity<Object> personSearch(@RequestBody final String term) {
        List<Person> list = repository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(term, term);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping(value = "/setStats", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Person> personStats(@RequestBody final Map<String,Object> stat_map) {
        // find ID
        long id=Long.parseLong((String)stat_map.get("id"));  
        Optional<Person> optional = repository.findById((id));
        if (optional.isPresent()) {  // Good ID
            Person person = optional.get();  // value from findByID

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
            person.setStats(date_map);  // BUG, needs to be customized to replace if existing or append if new
            repository.save(person);  // conclude by writing the stats updates

            // return Person with update Stats
            return new ResponseEntity<>(person, HttpStatus.OK);
        }
        // return Bad ID
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST); 
    }

    @GetMapping("/compareClassesWithPopulation/{personId}")
    public ResponseEntity<List<String>> compareClassesWithPopulation(@PathVariable Long personId) {
        Optional<Person> optionalPerson = repository.findById(personId);
    
        if (optionalPerson.isPresent()) {
            Person person = optionalPerson.get();
            List<Person> allPersons = repository.findAll();
            List<String> responseMessages = new ArrayList<>();
    
            for (Person otherPerson : allPersons) {
                if (!otherPerson.getId().equals(personId)) {
                    List<String> similarClasses = findSimilarClasses(person, otherPerson);
                    if (!similarClasses.isEmpty()) {
                        String message = String.format("You have classes with %s. Here are the classes you have together: %s",
                                                       otherPerson.getName(), similarClasses.toString());
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

    private List<String> findSimilarClasses(Person person1, Person person2) {
        Map<String, Map<String, Object>> stats1 = person1.getStats();
        Map<String, Map<String, Object>> stats2 = person2.getStats();

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

