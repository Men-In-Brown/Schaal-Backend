package com.nighthawk.spring_portfolio.mvc.calendar;

import org.springframework.web.bind.annotation.RestController;

import com.nighthawk.spring_portfolio.mvc.chat.Chat;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/calendar")
public class CalendarAPIController {

    @Autowired private CalendarJPARepository repository;
    @Autowired private EventJPARepository eventRepo;

    @GetMapping("/")
    public ResponseEntity<List<Calendar>> getAll() {
        List<Calendar> foundCalendars = repository.findAll();
        ArrayList<Calendar> returnable = new ArrayList<>();
        for (Calendar c : foundCalendars){
            Calendar toAdd = new Calendar(c.getName());
            toAdd.setId(c.getId());
            for (CalendarEvent ce : c.getEvents()){
                ce.setCalendar(null);
                toAdd.add(ce);
            }
            returnable.add(toAdd);
        }
        System.out.println(returnable);
        return new ResponseEntity<List<Calendar>>(returnable, HttpStatus.OK);
    }

    @GetMapping("/getEventsFrom")
    public ResponseEntity<List<CalendarEvent>> getWithinTime(@RequestParam int id, @RequestParam int duration) {
        Calendar c = repository.findByCalendarId(id);
        
        List<CalendarEvent> returnable = c.findEventsWithinTime(LocalDateTime.now(),Duration.ofHours(duration));

        return new ResponseEntity<List<CalendarEvent>>(returnable, HttpStatus.OK);
    }

    @GetMapping("/events")
    public ResponseEntity<List<CalendarEvent>> getAllEvents() {
        return new ResponseEntity<List<CalendarEvent>>(eventRepo.findAll(), HttpStatus.OK);
    }
    
}
