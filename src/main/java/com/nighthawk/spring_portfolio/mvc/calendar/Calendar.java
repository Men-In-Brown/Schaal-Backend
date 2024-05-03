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

    @OneToMany(mappedBy = "calendar", fetch = FetchType.EAGER)
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
        System.out.println(this.getEvents());
        // Calculate the end time based on the reference date and duration
        LocalDateTime endDate = referenceDate.plus(duration);
        List<CalendarEvent> valid = new ArrayList<CalendarEvent>();
        System.out.println("------");
        System.out.println(referenceDate);
        System.out.println(endDate);
        System.out.println("------");
        // Traverse the linked list to find the first event that starts after or at the end time
        for (CalendarEvent c : events){
            System.out.println(c.getStartDate().isAfter(referenceDate) && c.getEndDate().isBefore(endDate));
            if(c.getStartDate().isAfter(referenceDate) && (c.getEndDate().isBefore(endDate) || c.getEndDate().isEqual(endDate))){
                System.out.println(c);
                valid.add(c.giveAPI());
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

        Calendar c2 = new Calendar("Haseeb's Calendar");

        c2.add(new CalendarEvent("CSA", LocalDateTime.of(2024, 5, 2, 18, 0), LocalDateTime.of(2024, 5, 2, 20, 0), "Conference Room 1"));
        c2.add(new CalendarEvent("APEL", LocalDateTime.of(2024, 5, 3, 13, 0), LocalDateTime.of(2024, 5, 12, 15, 0), "Main Auditorium"));
        c2.add(new CalendarEvent("Paaras", LocalDateTime.of(2024, 5, 28, 19, 0), LocalDateTime.of(2024, 5, 12, 13, 0), "Cafeteria"));

        Calendar[] returnable = {myCalendar, c2};

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
