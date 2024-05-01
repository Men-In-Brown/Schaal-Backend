package com.nighthawk.spring_portfolio.mvc.calendar;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface EventJPARepository extends JpaRepository<CalendarEvent, Long>{
    List<CalendarEvent> findByName(String name);
}
