package com.nighthawk.spring_portfolio.mvc.betterhallpass;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nighthawk.spring_portfolio.mvc.person.Student;
import java.util.LinkedList;
import java.util.Queue;

@Service
public class HallPassService {
    private Queue<Student> inMemoryQueue = new LinkedList<>();

    @Autowired
    private HallPassEntryRepository HallPassEntryRepository;

    public void addToQueue(Student student) {
        inMemoryQueue.add(student);
        saveToDatabase(student);
    }

    public Student removeFromQueue() {
        Student student = inMemoryQueue.poll();
        removeFromDatabase(student);
        return student;
    }

    public Student peekNextInQueue() {
        return inMemoryQueue.peek();
    }

    public boolean isQueueEmpty() {
        return inMemoryQueue.isEmpty();
    }

    private void saveToDatabase(Student student) {
        HallPassEntry entry = new HallPassEntry();
        entry.setStudent(student);
        HallPassEntryRepository.save(entry);
    }

    private void removeFromDatabase(Student student) {
        HallPassEntry entry = HallPassEntryRepository.findByStudent(student);
        if (entry != null) {
            HallPassEntryRepository.delete(entry);
        }
    }
}