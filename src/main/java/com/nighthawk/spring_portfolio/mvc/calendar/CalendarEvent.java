package com.nighthawk.spring_portfolio.mvc.calendar;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import com.vladmihalcea.hibernate.type.json.JsonType;

import groovy.transform.Generated;
import groovyjarjarantlr4.v4.parse.ANTLRParser.labeledAlt_return;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.nighthawk.spring_portfolio.mvc.calendar.Calendar;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Convert(attributeName ="calendarevent", converter = JsonType.class)
public class CalendarEvent implements Serializable, Comparable<CalendarEvent>{

    @Id 
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name; 
    
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime startDate; 
    
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime endDate;

    private String location;

    @ManyToOne
    @JoinColumn(name = "calendar", referencedColumnName = "id")
    private Calendar calendar;

    //private CalendarEvent next; reference to the next node in the linked list implement if time
    public CalendarEvent(String name, LocalDateTime startDate, LocalDateTime endDate, String location){
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.location = location;
    }

    @Override
    public int compareTo(CalendarEvent calendarEvent) {
       if(startDate.isBefore(calendarEvent.getStartDate())){
            return 1;
       }
       else if (startDate.isAfter(calendarEvent.getStartDate())){
            return -1;
       }
       else {
            return 0;
       }
    }
    
    @Override
    public String toString(){
        return name;
    }

    public static CalendarEvent[] init(){
        CalendarEvent c1 =  new CalendarEvent("Meeting", LocalDateTime.of(2024, 4, 28, 10, 0), LocalDateTime.of(2024, 4, 28, 12, 0), "Conference Room 1");
        CalendarEvent c2 = new CalendarEvent("Presentation", LocalDateTime.of(2024, 4, 28, 13, 0), LocalDateTime.of(2024, 4, 28, 15, 0), "Main Auditorium");
        CalendarEvent c3 =  new CalendarEvent("Lunch", LocalDateTime.of(2024, 4, 28, 12, 0), LocalDateTime.of(2024, 4, 28, 13, 0), "Cafeteria");
        CalendarEvent[] inits = {c1, c2, c3};
        return inits;
    }
    public static void main(String args[]){
        // Creating two CalendarEvent objects
        CalendarEvent event1 = new CalendarEvent();
        event1.setName("Meeting");
        event1.setStartDate(LocalDateTime.of(2024, 4, 28, 10, 0));
        event1.setEndDate(LocalDateTime.of(2024, 4, 28, 12, 0));
        event1.setLocation("Conference Room 1");
    
        CalendarEvent event2 = new CalendarEvent();
        event2.setName("Presentation");
        event2.setStartDate(LocalDateTime.of(2024, 4, 28, 13, 0));
        event2.setEndDate(LocalDateTime.of(2024, 4, 28, 15, 0));
        event2.setLocation("Main Auditorium");
    
        // Comparing the two events
        int comparisonResult = event1.compareTo(event2);
        if(comparisonResult > 0) {
            System.out.println(event1.getName() + " starts before " + event2.getName());
        } else if(comparisonResult < 0) {
            System.out.println(event1.getName() + " starts after " + event2.getName());
        } else {
            System.out.println(event1.getName() + " starts at the same time as " + event2.getName());
        }
    }
}
