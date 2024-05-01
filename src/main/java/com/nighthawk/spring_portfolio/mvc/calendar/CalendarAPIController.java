package com.nighthawk.spring_portfolio.mvc.calendar;

import org.springframework.web.bind.annotation.RestController;

import com.nighthawk.spring_portfolio.mvc.chat.Chat;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/calendar")
public class CalendarAPIController {

    @Autowired
    private CalendarJPARepository repository;

    @GetMapping("/")
    public ResponseEntity<List<Calendar>> getALl() {
        return new ResponseEntity<List<Calendar>>(repository.findAll(), HttpStatus.OK);
    }
    
}
