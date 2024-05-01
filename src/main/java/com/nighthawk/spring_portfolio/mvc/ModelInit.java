package com.nighthawk.spring_portfolio.mvc;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import com.nighthawk.spring_portfolio.mvc.calendar.Calendar;
import com.nighthawk.spring_portfolio.mvc.calendar.CalendarEvent;
import com.nighthawk.spring_portfolio.mvc.calendar.CalendarJPARepository;
import com.nighthawk.spring_portfolio.mvc.calendar.CalendarService;
import com.nighthawk.spring_portfolio.mvc.calendar.EventJPARepository;
// import com.nighthawk.spring_portfolio.mvc.calendar.Calendar;
// import com.nighthawk.spring_portfolio.mvc.calendar.CalendarJPARepository;
import com.nighthawk.spring_portfolio.mvc.jokes.Jokes;
import com.nighthawk.spring_portfolio.mvc.jokes.JokesJpaRepository;
import com.nighthawk.spring_portfolio.mvc.linkrAuthentication.LinkrPAT;
import com.nighthawk.spring_portfolio.mvc.linkrAuthentication.PatJpaRepository;
import com.nighthawk.spring_portfolio.mvc.note.Note;
import com.nighthawk.spring_portfolio.mvc.note.NoteJpaRepository;
import com.nighthawk.spring_portfolio.mvc.person.Person;
import com.nighthawk.spring_portfolio.mvc.person.PersonDetailsService;
import com.nighthawk.spring_portfolio.mvc.person.PersonRole;
import com.nighthawk.spring_portfolio.mvc.person.PersonRoleJpaRepository;

import java.util.List;

@Component
@Configuration // Scans Application for ModelInit Bean, this detects CommandLineRunner
public class ModelInit {  
    @Autowired JokesJpaRepository jokesRepo;
    @Autowired NoteJpaRepository noteRepo;
    @Autowired PersonDetailsService personService;
    @Autowired PersonRoleJpaRepository roleRepo;
    @Autowired CalendarJPARepository calendarRepo;
    @Autowired EventJPARepository calendarEventRepository;
    @Autowired PatJpaRepository patRepo;
    
    @Bean
    CommandLineRunner run() {  // The run() method will be executed after the application starts
        return args -> {

            // Joke database is populated with starting jokes
            String[] jokesArray = Jokes.init();
            for (String joke : jokesArray) {
                List<Jokes> jokeFound = jokesRepo.findByJokeIgnoreCase(joke);  // JPA lookup
                if (jokeFound.size() == 0)
                    jokesRepo.save(new Jokes(null, joke, 0, 0)); //JPA save
            }

            // adding roles
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

    
            LinkrPAT[] list = LinkrPAT.init();
            for(LinkrPAT l : list){
                List<LinkrPAT> found = patRepo.findAllByUser(l.getUser());
                if(found.size() == 0){
                    patRepo.save(l);
                }
            }
            System.out.println("helo");
            Calendar[] clist = Calendar.initCalendars();
            for(Calendar c : clist){
                List<Calendar> found = calendarRepo.findByName(c.getName());
                if (found.size() == 0){
                    for(CalendarEvent event : c.getEvents()){
                        if(event.getCalendar() == null){
                            System.out.println("Yes!");
                            calendarRepo.save(c);
                            event.setCalendar(c);
                        }
                        calendarEventRepository.save(event);
                    }
                    
                }
            }
            System.out.println("kil me");

            // Person database is populated with test data
            Person[] personArray = Person.init();
            for (Person person : personArray) {
                //findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase
                List<Person> personFound = personService.list(person.getName(), person.getEmail());  // lookup
                if (personFound.size() == 0) {
                    personService.save(person);  // save

                    // Each "test person" starts with a "test note"
                    String text = "Test " + person.getEmail();
                    Note n = new Note(text, person);  // constructor uses new person as Many-to-One association
                    noteRepo.save(n);  // JPA Save
                    personService.addRoleToPerson(person.getEmail(), "ROLE_STUDENT");
                }
            }
            System.out.println("Person done");
            // for lesson demonstration: giving admin role to Mortensen
            personService.addRoleToPerson(personArray[1].getEmail(), "ROLE_ADMIN");

            
        };
    }
}

