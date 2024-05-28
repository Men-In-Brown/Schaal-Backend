package com.nighthawk.spring_portfolio.mvc;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.junit.experimental.theories.internal.Assignments;
import org.springframework.beans.factory.annotation.Autowired;

import com.nighthawk.spring_portfolio.mvc.assignment.Assignment;
import com.nighthawk.spring_portfolio.mvc.assignment.AssignmentJpaRepository;
import com.nighthawk.spring_portfolio.mvc.grade.Grade;
import com.nighthawk.spring_portfolio.mvc.grade.GradeJpaRepository;

import com.nighthawk.spring_portfolio.mvc.linkrAuthentication.LinkrPAT;
import com.nighthawk.spring_portfolio.mvc.linkrAuthentication.PatJpaRepository;

import com.nighthawk.spring_portfolio.mvc.note.Note;
import com.nighthawk.spring_portfolio.mvc.note.NoteJpaRepository;
import com.nighthawk.spring_portfolio.mvc.person.Person;
import com.nighthawk.spring_portfolio.mvc.person.PersonDetailsService;
import com.nighthawk.spring_portfolio.mvc.person.PersonRole;
import com.nighthawk.spring_portfolio.mvc.person.PersonRoleJpaRepository;

import java.util.ArrayList;
import java.util.List;

@Component
@Configuration // Scans Application for ModelInit Bean, this detects CommandLineRunner
public class ModelInit {  
    @Autowired AssignmentJpaRepository assignmentRepo;
    @Autowired GradeJpaRepository gradeRepo;
    @Autowired NoteJpaRepository noteRepo;
    @Autowired PersonRoleJpaRepository roleJpaRepository;
    @Autowired PersonDetailsService personDetailsService;
    @Autowired PatJpaRepository patRepo;

    @Bean
    @Transactional
    CommandLineRunner run() {  // The run() method will be executed after the application starts
        return args -> {

            // Joke database is populated with starting jokes
            /* String[] jokesArray = Assignments.init();
            for (String joke : jokesArray) {
                List<Assigment>jokeFound = jokesRepo.findByJokeIgnoreCase(joke);  // JPA lookup
                if (jokeFound.size() == 0)
                    jokesRepo.save(new Assigment(null, joke, 0, 0)); //JPA save
            } */

            // Person database is populated with starting people
            Person[] personArray = Person.init();
            for (Person person : personArray) {
                // Name and email are used to lookup the person
                List<Person> personFound = personDetailsService.list(person.getName(), person.getEmail());  // lookup
                if (personFound.size() == 0) { // add if not found
                    // Roles are added to the database if they do not exist
                    List<PersonRole> updatedRoles = new ArrayList<>();
                    for (PersonRole role : person.getRoles()) {
                        // Name is used to lookup the role
                        PersonRole roleFound = roleJpaRepository.findByName(role.getName());  // JPA lookup
                        if (roleFound == null) { // add if not found
                            // Save the new role to database
                            roleJpaRepository.save(role);  // JPA save
                            roleFound = role;
                        }
                        // Accumulate reference to role from database
                        updatedRoles.add(roleFound);
                    }
                    // Update person with roles from role databasea
                    person.setRoles(updatedRoles); // Object reference is updated

                    // Save person to database
                    personDetailsService.save(person); // JPA save

                    // Add a "test note" for each new person
                    String text = "Test " + person.getEmail();
                    Note n = new Note(text, person);  // constructor uses new person as Many-to-One association
                    noteRepo.save(n);  // JPA Save  
		            //personService.addRoleToPerson(person.getEmail(), "ROLE_STUDENT");                
                }
            }
	    // for lesson demonstration: giving admin role to Mortensen
            //personService.addRoleToPerson(personArray[4].getEmail(), "ROLE_ADMIN");

            //delete all entries from grade database
            // gradeRepo.deleteAll();
            // Grade[] gradeArray = Grade.init();
            // for (Grade score : gradeArray) {
            //     //List<Grade> test = gradeRepo.list(score.getName());  // lookup
            //     //if (test.size() == 0) {
            //         gradeRepo.save(score);
            //     //};
            // }

            //delete all entries from grade database
            /*assignmentRepo.deleteAll();
            Assignment[] assignmentArray = Assignment.init();
            for (Assignment score : assignmentArray) {
                assignmentRepo.save(score);
            }*/

            LinkrPAT[] lst = LinkrPAT.init();
            for(LinkrPAT l : lst){
                List<LinkrPAT> found = patRepo.findAllByUser(l.getUser());
                if(found.size() == 0){
                    patRepo.save(l);
                }
            }
        };

        
    }
}
