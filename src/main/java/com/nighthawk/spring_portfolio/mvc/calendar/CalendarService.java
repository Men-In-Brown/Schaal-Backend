package com.nighthawk.spring_portfolio.mvc.calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CalendarService {
    @Autowired
    private CalendarJPARepository myRepository;
    
    @Transactional
    public void save(Calendar calendar) {
        myRepository.save(calendar);
    }
}
