package com.nighthawk.spring_portfolio.mvc.calendar;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class Calendar {
    private CalendarEvent head;

    public void addEvent(CalendarEvent event) {
        if (head == null) {
            head = event;
        } else {
            CalendarEvent current = head;
            while (current.getNext() != null) {
                current = current.getNext();
            }
            current.setNext(event);
        }
    }

    public List<CalendarEvent> findEventsWithinTime(LocalDateTime referenceDate, Duration duration) {
        List<CalendarEvent> eventsWithinTime = new ArrayList<>();
    
        if (head == null) {
            return eventsWithinTime; // Return an empty list if the calendar is empty
        }
    
        // Calculate the end time based on the reference date and duration
        LocalDateTime endDate = referenceDate.plus(duration);
    
        // Traverse the linked list to find all events within the specified time frame
        CalendarEvent current = head;
        while (current != null && !current.getStartTime().isAfter(endDate)) {
            // If the current event's end time is after the reference date,
            // add it to the list of events within the time frame
            if (!current.getEndTime().isBefore(referenceDate)) {
                eventsWithinTime.add(current);
            }
            current = current.getNext();
        }
    
        return eventsWithinTime; // Return the list of events within the specified time frame
    }

    public CalendarEvent getHead() {
        return head;
    }

    public void setHead(CalendarEvent head) {
        this.head = head;
    }

    public static void main(String[] args) {
        Calendar calendar = new Calendar();

        // Creating sample events
        CalendarEvent event1 = new CalendarEvent("Event 1", LocalDateTime.of(2024, 4, 21, 10, 0), LocalDateTime.of(2024, 4, 21, 11, 0));
        CalendarEvent event2 = new CalendarEvent("Event 2", LocalDateTime.of(2024, 4, 21, 12, 0), LocalDateTime.of(2024, 4, 21, 14, 0));
        CalendarEvent event3 = new CalendarEvent("Event 3", LocalDateTime.of(2024, 4, 22, 15, 0), LocalDateTime.of(2024, 4, 21, 16, 0));

        // Adding events to the calendar
        calendar.addEvent(event1);
        calendar.addEvent(event2);
        calendar.addEvent(event3);

        // Testing findEventWithinTime method
        LocalDateTime referenceDate = LocalDateTime.of(2024, 4, 21, 10, 0); // Reference date, should be set to current date

        Duration duration = Duration.ofHours(2); // Duration

        List<CalendarEvent> foundEvents = calendar.findEventsWithinTime(referenceDate, duration);

        for (CalendarEvent event : foundEvents){
            System.out.println(event);
        }
    }
}
