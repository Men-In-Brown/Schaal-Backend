package com.nighthawk.spring_portfolio.mvc.calendar;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CalendarJPARepository extends JpaRepository<Calendar, Long>{
    
    List<Calendar> findAll();
    List<Calendar> findByName(String name);



}
