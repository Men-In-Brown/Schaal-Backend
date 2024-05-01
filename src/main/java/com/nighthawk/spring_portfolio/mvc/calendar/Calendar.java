package com.nighthawk.spring_portfolio.mvc.calendar;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import com.vladmihalcea.hibernate.type.json.JsonType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Convert(attributeName ="calendar", converter = JsonType.class)
public class Calendar {

    @Id 
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(mappedBy = "calendar", fetch = FetchType.LAZY)
    @Column(name = "events")
    private List<CalendarEvent> events; 

    @Column(name="name")
    private String name;

    public boolean add(CalendarEvent cEvent){
        if(events.add(cEvent)){
            return true;
        }
        else {
            return false;
        }
    }

    public Calendar(String name) {
        this.name = name; 
        this.events = new ArrayList<CalendarEvent>();
    }
    
    public List<CalendarEvent> findEventsWithinTime(LocalDateTime referenceDate, Duration duration) {
        if (events.size() == 0) {
            return null;
        }

        // Calculate the end time based on the reference date and duration
        LocalDateTime endDate = referenceDate.plus(duration);
        List<CalendarEvent> valid = new ArrayList<CalendarEvent>();
        System.out.println("------");
        System.out.println(referenceDate);
        System.out.println(endDate);
        System.out.println("------");
        // Traverse the linked list to find the first event that starts after or at the end time
        for (CalendarEvent c : events){
            if(c.getStartDate().isAfter(referenceDate) && (c.getEndDate().isBefore(endDate) || c.getEndDate().isEqual(endDate))){
                System.out.println(c.getStartDate());
                System.out.println(c.getEndDate());
                valid.add(c);
            }
        }

        return valid; // Return the first event found within the specified duration
    }

    public static Calendar[] initCalendars(){
        Calendar myCalendar = new Calendar("My Calendar");

        // Adding some events to the calendar
        myCalendar.add(new CalendarEvent("Meeting", LocalDateTime.of(2024, 4, 28, 10, 0), LocalDateTime.of(2024, 4, 28, 12, 0), "Conference Room 1"));
        myCalendar.add(new CalendarEvent("Presentation", LocalDateTime.of(2024, 4, 28, 13, 0), LocalDateTime.of(2024, 4, 28, 15, 0), "Main Auditorium"));
        myCalendar.add(new CalendarEvent("Lunch", LocalDateTime.of(2024, 4, 28, 12, 0), LocalDateTime.of(2024, 4, 28, 13, 0), "Cafeteria"));

        Calendar[] returnable = {myCalendar};

        return returnable;
    }
    public static void main(String[] args) {
        // Creating a calendar
        Calendar myCalendar = new Calendar((long) 1, new ArrayList<>(), "My Calendar");

        // Adding some events to the calendar
        myCalendar.add(new CalendarEvent("Meeting", LocalDateTime.of(2024, 4, 28, 10, 0), LocalDateTime.of(2024, 4, 28, 12, 0), "Conference Room 1"));
        myCalendar.add(new CalendarEvent( "Presentation", LocalDateTime.of(2024, 4, 28, 13, 0), LocalDateTime.of(2024, 4, 28, 15, 0), "Main Auditorium"));
        myCalendar.add(new CalendarEvent("Lunch", LocalDateTime.of(2024, 4, 28, 12, 0), LocalDateTime.of(2024, 4, 28, 13, 0), "Cafeteria"));

        // Finding events within a specific time range
        LocalDateTime referenceDate = LocalDateTime.of(2024, 4, 28, 9, 0);
        Duration duration = Duration.ofHours(3);
        for(CalendarEvent c : myCalendar.findEventsWithinTime(referenceDate, duration)){
            System.out.println(c.getName());
        }
    }
}
