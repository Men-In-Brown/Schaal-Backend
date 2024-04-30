package com.nighthawk.spring_portfolio.mvc.calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CalendarService {
    @Autowired
    private CalendarJPARepository myRepository;

    public void save(Calendar c){
        myRepository.save(c);
    }
}
