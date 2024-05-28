package com.nighthawk.spring_portfolio.mvc.teacher;

import java.util.Date;

import com.nighthawk.spring_portfolio.mvc.person.Person;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Teacher extends Person {

    private String subject;

    public Teacher(String email, String password, String name, Date dob, String subject) {
        super(email, password, name, dob);
        this.subject = subject;
    }

    public static Teacher[] init() {
        Teacher t1 = new Teacher();
        t1.setEmail("teacher1@gmail.com");
        t1.setPassword("123teacher");
        t1.setName("Mr. Smith");
        t1.setDob(new Date());
        t1.setSubject("Mathematics");

        Teacher t2 = new Teacher();
        t2.setEmail("teacher2@gmail.com");
        t2.setPassword("123teacher");
        t2.setName("Ms. Johnson");
        t2.setDob(new Date());
        t2.setSubject("Science");

        return new Teacher[]{t1, t2};
    }

    @Override
    public int getAge() {
        if (getDob() != null) {
            long ageInMillis = new Date().getTime() - getDob().getTime();
            return (int) (ageInMillis / 1000 / 60 / 60 / 24 / 365);
        }
        return -1; // Return default value if dob is null
    }
}