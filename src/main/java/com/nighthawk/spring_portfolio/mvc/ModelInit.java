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
    @Autowired PersonRoleJpaRepository roleJpaRepository;
    @Autowired PersonDetailsService personDetailsService;
    @Autowired PersonJpaRepository personJpaRepository;
    @Autowired InternshipRepository internshipRepository;
    @Autowired PasswordEncoder passwordEncoder;
    @Autowired StudentJpaRepository studentJpaRepository;
    @Autowired TeacherJpaRepository teacherJpaRepository;
    @Autowired InternJpaRepository internJpaRepository;
    @Autowired AdminJpaRepository adminJpaRepository;


    @Bean
    @Transactional
    CommandLineRunner run() {  // The run() method will be executed after the application starts
        return args -> {

            // Joke database is populated with starting jokes
            String[] jokesArray = Jokes.init();
            for (String joke : jokesArray) {
                List<Jokes> jokeFound = jokesRepo.findByJokeIgnoreCase(joke);  // JPA lookup
                if (jokeFound.size() == 0)
                    jokesRepo.save(new Jokes(null, joke, 0, 0)); //JPA save
            }

            // Person database is populated with starting people
            Person[] personArray = Person.init();
            for (Person person : personArray) {
                // Name and email are used to lookup the person
                System.out.println(person.getRoles()); 
                List<Person> personFound = personDetailsService.list(person.getName(), person.getEmail());  // lookup
                if (personFound.size() == 0) { // add if not found
                    // Roles are added to the database if they do not exist
                   System.out.println(person.getRoles()); 
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
                    // Update person with roles from role database
                    person.setRoles(updatedRoles); // Object reference is updated

                    // Save person to database
                    personDetailsService.save(person); // JPA save

                    // Add a "test note" for each new person
                    String text = "Test " + person.getEmail();
                    Note n = new Note(text, person);  // constructor uses new person as Many-to-One association
                    noteRepo.save(n);  // JPA Save                  
                }
            }
            Admin[] adminArray = Admin.init();
            for (Admin admin : adminArray) {
                List<Person> adminFound = personDetailsService.list(admin.getName(), admin.getEmail());
                if (adminFound.size() == 0) {
                    List<PersonRole> updatedRoles = new ArrayList<>();
                    for (PersonRole role : admin.getRoles()) {
                        PersonRole roleFound = roleJpaRepository.findByName(role.getName());
                        if (roleFound == null) {
                            roleJpaRepository.save(role);
                            roleFound = role;
                    }
                    updatedRoles.add(roleFound);
                }
                admin.setRoles(updatedRoles);
                personDetailsService.save(admin);
                String text = "Test " + admin.getEmail();
                Note n = new Note(text, admin);
                noteRepo.save(n);
            }
        }
            Intern[] internArray = Intern.init();
            for (Intern intern : internArray) {
                List<Person> internFound = personDetailsService.list(intern.getName(), intern.getEmail());
                if (internFound.size() == 0) {
                    List<PersonRole> updatedRoles = new ArrayList<>();
                    for (PersonRole role : intern.getRoles()) {
                        PersonRole roleFound = roleJpaRepository.findByName(role.getName());
                        if (roleFound == null) {
                            roleJpaRepository.save(role);
                            roleFound = role;
                    }
                    updatedRoles.add(roleFound);
                }
                intern.setRoles(updatedRoles);
                personDetailsService.save(intern);
                String text = "Test " + intern.getEmail();
                Note n = new Note(text, intern);
                noteRepo.save(n);
            }
        }
            Student[] studentArray = Student.init();
            for (Student student : studentArray) {
                List<Person> studentFound = personDetailsService.list(student.getName(), student.getEmail());
                if (studentFound.size() == 0) {
                    List<PersonRole> updatedRoles = new ArrayList<>();
                    for (PersonRole role : student.getRoles()) {
                        PersonRole roleFound = roleJpaRepository.findByName(role.getName());
                        if (roleFound == null) {
                            roleJpaRepository.save(role);
                            roleFound = role;
                    }
                    updatedRoles.add(roleFound);
                }
                student.setRoles(updatedRoles);
                personDetailsService.save(student);
                String text = "Test " + student.getEmail();
                Note n = new Note(text, student);
                noteRepo.save(n);
            }
        }
            Teacher[] teacherArray = Teacher.init();
            for (Teacher teacher : teacherArray) {
                List<Person> teacherFound = personDetailsService.list(teacher.getName(), teacher.getEmail());
                if (teacherFound.size() == 0) {
                    List<PersonRole> updatedRoles = new ArrayList<>();
                    for (PersonRole role : teacher.getRoles()) {
                        PersonRole roleFound = roleJpaRepository.findByName(role.getName());
                        if (roleFound == null) {
                            roleJpaRepository.save(role);
                            roleFound = role;
                    }
                    updatedRoles.add(roleFound);
                }
                teacher.setRoles(updatedRoles);
                personDetailsService.save(teacher);
                String text = "Test " + teacher.getEmail();
                Note n = new Note(text, teacher);
                noteRepo.save(n);
            }
        }
    };
}
}