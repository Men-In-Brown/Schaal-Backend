package com.nighthawk.spring_portfolio.mvc.betterhallpasscopy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.LinkedList;
import java.util.Queue;

@Service
public class HallPassService {
    private Queue<Long> inMemoryQueue = new LinkedList<>();

    public boolean addToQueue(Long studentId) {
        // You can add validation logic here if needed, e.g., check for duplicate student IDs
        inMemoryQueue.add(studentId);
        return true; // or return false if validation fails
    }

    public Long removeFromQueue() {
        return inMemoryQueue.poll();
    }

    public Long peekNextInQueue() {
        return inMemoryQueue.peek();
    }

    public boolean isQueueEmpty() {
        return inMemoryQueue.isEmpty();
    }
}