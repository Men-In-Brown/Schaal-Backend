package com.nighthawk.spring_portfolio.mvc.calendar;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;


@Repository
public interface CalendarJPARepository extends JpaRepository<Calendar, Long>{

    Calendar findByName(Long id);
    
    @Override
    List<Calendar> findAll();

    




    
}
