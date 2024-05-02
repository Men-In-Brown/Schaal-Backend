package com.nighthawk.spring_portfolio.mvc.calendar;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CalendarJPARepository extends JpaRepository<Calendar, Long>{
    
    List<Calendar> findAll();
    List<Calendar> findByName(String name);

    @Query("SELECT c FROM Calendar c WHERE c.id = :id")
    Calendar findByCalendarId(@Param("id") long id);


}
