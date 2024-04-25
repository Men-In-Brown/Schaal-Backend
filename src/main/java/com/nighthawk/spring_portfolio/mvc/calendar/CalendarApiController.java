package com.nighthawk.spring_portfolio.mvc.calendar;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nighthawk.spring_portfolio.mvc.linkr.User;

import org.springframework.web.bind.annotation.RequestParam;


/** Calendar API
 * Calendar Endpoint: /api/calendar/isLeapYear/2022, Returns: {"year":2020,"isLeapYear":false}
 */
@RestController
@RequestMapping("/api/calendar")
public class CalendarApiController {

  @Autowired
  private CalendarJPARepository repository;

  @GetMapping("/")
  public ResponseEntity<List<Calendar>> getAllCalendars() {
      return ResponseEntity.ok().body(repository.findAll());
  }

  @GetMapping("/")
  public ResponseEntity<Calendar> findSpecificCalendar(@RequestParam String name) {
      return ResponseEntity.ok().body(repository.findByName(name));
  }
  
  
  @GetMapping("/")
  public ResponseEntity<Calendar> getByUser(@RequestParam User user) {
      Calendar found = repository.findByUser();
      return ResponseEntity.ok().body(found);
  }
 


  

   

    // add other methods
}