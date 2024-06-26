package com.nighthawk.spring_portfolio.mvc;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.junit.experimental.theories.internal.Assignments;
import org.springframework.beans.factory.annotation.Autowired;

import com.nighthawk.spring_portfolio.mvc.assignment.Assignment;
import com.nighthawk.spring_portfolio.mvc.assignment.AssignmentJpaRepository;
import com.nighthawk.spring_portfolio.mvc.grade.Grade;
import com.nighthawk.spring_portfolio.mvc.grade.GradeJpaRepository;
import com.nighthawk.spring_portfolio.mvc.linkr.Internship;
import com.nighthawk.spring_portfolio.mvc.linkr.InternshipRepository;
import com.nighthawk.spring_portfolio.mvc.linkrAuthentication.LinkrPAT;
import com.nighthawk.spring_portfolio.mvc.linkrAuthentication.PatJpaRepository;

import com.nighthawk.spring_portfolio.mvc.linkr.Internship;
import com.nighthawk.spring_portfolio.mvc.linkr.InternshipRepository;
import com.nighthawk.spring_portfolio.mvc.note.Note;
import com.nighthawk.spring_portfolio.mvc.note.NoteJpaRepository;
import com.nighthawk.spring_portfolio.mvc.person.Person;
import com.nighthawk.spring_portfolio.mvc.person.PersonDetailsService;
import com.nighthawk.spring_portfolio.mvc.person.PersonJpaRepository;
import com.nighthawk.spring_portfolio.mvc.person.PersonRole;
import com.nighthawk.spring_portfolio.mvc.person.PersonRoleJpaRepository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@Configuration // Scans Application for ModelInit Bean, this detects CommandLineRunner
public class ModelInit {  
    @Autowired AssignmentJpaRepository assignmentRepo;
    @Autowired GradeJpaRepository gradeRepo;
    @Autowired NoteJpaRepository noteRepo;
    @Autowired PersonRoleJpaRepository roleRepo;
    @Autowired PersonDetailsService personService;
    @Autowired PatJpaRepository patRepo;
    @Autowired InternshipRepository internshipRepo;
    @Autowired PersonJpaRepository personJpaRepository;
    @Autowired InternshipRepository internshipRepository;
    @Autowired PasswordEncoder passwordEncoder;

    @Bean
    @Transactional
    CommandLineRunner run() {  // The run() method will be executed after the application starts
        return args -> {
            PersonRole[] personRoles = PersonRole.init();
            for (PersonRole role : personRoles) {
                PersonRole existingRole = roleRepo.findByName(role.getName());
                if (existingRole != null) {
                    // role already exists
                    continue;
                } else {
                    // role doesn't exist
                    roleRepo.save(role);
                }
            }

            // Person database is populated with test data
            Person[] personArray = Person.init();
            for (Person person : personArray) {
                //findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase
                List<Person> personFound = personService.list(person.getName(), person.getEmail());  // lookup
                if (personFound.size() == 0) {
                    personService.save(person);  // save

                    personService.addRoleToPerson(person.getEmail(), "ROLE_USER");
                }
            }
            // for testing: giving admin role to Mortensen
            personService.addRoleToPerson(personArray[4].getEmail(), "ROLE_ADMIN");


        // Internship database is populated with starting internships
            Internship[] internshipArray = Internship.internshipInit();
            for (Internship internship : internshipArray) {
                internshipRepo.save(internship); // JPA save
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