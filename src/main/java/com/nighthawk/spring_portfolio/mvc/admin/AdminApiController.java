package com.nighthawk.spring_portfolio.mvc.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/api/Admin")
public class AdminApiController {

    @Autowired
    private AdminJpaRepository repository;

    @GetMapping("/")
    public ResponseEntity<List<Admin>> getPeople() {
        return new ResponseEntity<>(repository.findAllByOrderByNameAsc(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Admin> getAdmin(@PathVariable long id) {
        Optional<Admin> optional = repository.findById(id);
        if (optional.isPresent()) {
            Admin Admin = optional.get();
            return new ResponseEntity<>(Admin, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Admin> deleteAdmin(@PathVariable long id) {
        Optional<Admin> optional = repository.findById(id);
        if (optional.isPresent()) {
            Admin Admin = optional.get();
            repository.deleteById(id);
            return new ResponseEntity<>(Admin, HttpStatus.OK);
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
        
            // Create and save Admin entity
            Admin admin = new Admin(null, request.getEmail(), request.getPassword(), request.getName(), dob, null, null, null);
            admin.getRoles().add(role); // Add the admin to the person
            repository.save(admin);
        
            return new ResponseEntity<>(request.getEmail() + " is created successfully", HttpStatus.CREATED);
        }    


    @PostMapping("/search")
    public ResponseEntity<Object> AdminSearch(@RequestBody final String term) {
        List<Admin> list = repository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(term, term);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping(value = "/setStats", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Admin> AdminStats(@RequestBody final Map<String,Object> stat_map) {
        // find ID
        long id=Long.parseLong((String)stat_map.get("id"));  
        Optional<Admin> optional = repository.findById((id));
        if (optional.isPresent()) {  // Good ID
            Admin Admin = optional.get();  // value from findByID

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
            Admin.setStats(date_map);  // BUG, needs to be customized to replace if existing or append if new
            repository.save(Admin);  // conclude by writing the stats updates

            // return Admin with update Stats
            return new ResponseEntity<>(Admin, HttpStatus.OK);
        }
        // return Bad ID
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST); 
    }

    @GetMapping("/compareClassesWithPopulation/{AdminId}")
    public ResponseEntity<List<String>> compareClassesWithPopulation(@PathVariable Long AdminId) {
        Optional<Admin> optionalAdmin = repository.findById(AdminId);
    
        if (optionalAdmin.isPresent()) {
            Admin Admin = optionalAdmin.get();
            List<Admin> allAdmins = repository.findAll();
            List<String> responseMessages = new ArrayList<>();
    
            for (Admin otherAdmin : allAdmins) {
                if (!otherAdmin.getId().equals(AdminId)) {
                    List<String> similarClasses = findSimilarClasses(Admin, otherAdmin);
                    if (!similarClasses.isEmpty()) {
                        String message = String.format("You have classes with %s. Here are the classes you have together: %s",
                                                       otherAdmin.getName(), similarClasses.toString());
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

    private List<String> findSimilarClasses(Admin Admin1, Admin Admin2) {
        Map<String, Map<String, Object>> stats1 = Admin1.getStats();
        Map<String, Map<String, Object>> stats2 = Admin2.getStats();

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

