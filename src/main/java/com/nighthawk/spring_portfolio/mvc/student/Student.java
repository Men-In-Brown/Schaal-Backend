package com.nighthawk.spring_portfolio.mvc.student;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.nighthawk.spring_portfolio.mvc.linkr.Internship;
import com.nighthawk.spring_portfolio.mvc.person.Person;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapKeyColumn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor // Lombok will generate the no-argument constructor
@AllArgsConstructor
@Entity
public class Student extends Person {

    private int grade;
    private String interest;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "student_internships", joinColumns = @JoinColumn(name = "student_id"))
    private ArrayList<Internship> internships = new ArrayList<>(); // Initialize here

    @ElementCollection
    @CollectionTable(name = "student_classes", joinColumns = @JoinColumn(name = "student_id"))
    @MapKeyColumn(name = "class_key")
    @Column(name = "class_value")
    private Map<String, String> classes = new HashMap<>(); // Initialize here

    public Student(String email, String password, String name, Date dob, int grade, String interest) {
        super(email, password, name, dob);
        this.grade = grade;
        this.interest = interest;
        this.classes = new HashMap<>(); 
        this.internships = new ArrayList<>();
    }

    public static Student[] init() {
        Student s1 = new Student();
        s1.setEmail("spark@gmail.com");
        s1.setPassword("123spark");
        s1.setName("Spark User");
        s1.setDob(new Date());
        s1.setGrade(10);
        s1.getClasses().put("class 1", "csa");
        s1.getClasses().put("class 2", "csp");
        s1.getClasses().put("class 3", "csse");
        s1.getClasses().put("class 4", "calcab");
        s1.getClasses().put("class 5", "phys");
        s1.getInternships().add(new Internship("Name 1", "To Name", "California", "Tech", "None"));
        s1.getInternships().add(new Internship("Name 2", "To not name", "India", "Hosptality", "Tanay"));
        s1.getInternships().add(new Internship("Name 3", "That is the question", "Shanghai", "Tax Fraud", "Paaras"));

        Student s2 = new Student();
        s2.setEmail("mort@gmail.com");
        s2.setPassword("123mort");
        s2.setName("John Mortensen");
        s2.setDob(new Date());
        s2.setGrade(12);
        s2.getClasses().put("class 1", "csa");
        s2.getClasses().put("class 2", "csp");
        s2.getClasses().put("class 3", "csse");
        s2.getClasses().put("class 4", "calcab");
        s2.getClasses().put("class 5", "phys");
        s2.getInternships().add(new Internship("Name 4", "Mission 4", "Location 4", "Industry 4", "CEO 4"));
        s2.getInternships().add(new Internship("Name 5", "Mission 5", "Location 5", "Industry 5", "CEO 5"));

        return new Student[]{s1, s2};
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