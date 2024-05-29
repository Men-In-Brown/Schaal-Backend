package com.nighthawk.spring_portfolio.mvc;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import com.nighthawk.spring_portfolio.mvc.jokes.Jokes;
import com.nighthawk.spring_portfolio.mvc.jokes.JokesJpaRepository;
import com.nighthawk.spring_portfolio.mvc.linkr.Internship;
import com.nighthawk.spring_portfolio.mvc.linkr.InternshipRepository;
import com.nighthawk.spring_portfolio.mvc.note.Note;
import com.nighthawk.spring_portfolio.mvc.note.NoteJpaRepository;
import com.nighthawk.spring_portfolio.mvc.admin.Admin;
import com.nighthawk.spring_portfolio.mvc.admin.AdminJpaRepository;
import com.nighthawk.spring_portfolio.mvc.intern.Intern;
import com.nighthawk.spring_portfolio.mvc.intern.InternJpaRepository;
import com.nighthawk.spring_portfolio.mvc.person.Person;
import com.nighthawk.spring_portfolio.mvc.person.PersonDetailsService;
import com.nighthawk.spring_portfolio.mvc.person.PersonJpaRepository;
import com.nighthawk.spring_portfolio.mvc.person.PersonRole;
import com.nighthawk.spring_portfolio.mvc.person.PersonRoleJpaRepository;
import com.nighthawk.spring_portfolio.mvc.student.Student;
import com.nighthawk.spring_portfolio.mvc.student.StudentJpaRepository;
import com.nighthawk.spring_portfolio.mvc.teacher.Teacher;
import com.nighthawk.spring_portfolio.mvc.teacher.TeacherJpaRepository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@Configuration // Scans Application for ModelInit Bean, this detects CommandLineRunner
public class ModelInit {  
    @Autowired JokesJpaRepository jokesRepo;
    @Autowired NoteJpaRepository noteRepo;
    @Autowired PersonRoleJpaRepository roleRepo;
    @Autowired PersonDetailsService personService;
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

            // initializing classPeriod objects
            String[] emailsForInit = {"toby@gmail.com", "jm1021@gmail.com"};
            String[] emailsForStudent = {"toby@gmail.com", "lexb@gmail.com", "niko@gmail.com", "madam@gmail.com", "jm1021@gmail.com"};
            int i = 0;
            ClassPeriod[] classPeriods = ClassPeriod.init();
            for (ClassPeriod classPeriod : classPeriods) {
                ClassPeriod existingClass = classRepo.findByName(classPeriod.getName());
                if (existingClass != null) {
                    // class already exists
                    i++;
                    continue;
                } else {
                    // class doesn't exist
                    classService.save(classPeriod);
                    classService.addLeaderToClass(emailsForInit[i], classPeriod.getId());
                    for (int j = 4 - i; j >= 1 - i; j--) {
                        classService.addStudentToClass(emailsForStudent[j], classPeriod.getId());
                    }
                    i++;
                }
            }
    };
}
}