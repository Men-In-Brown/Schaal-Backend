package com.nighthawk.spring_portfolio.mvc.assignment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.nighthawk.spring_portfolio.mvc.grade.Grade;

import jakarta.persistence.*;

@Data  // Annotations to simplify writing code (ie constructors, setters)
@NoArgsConstructor
@AllArgsConstructor
@Entity // Annotation to simplify creating an entity, which is a lightweight persistence domain object. Typically, an entity represents a table in a relational database, and each entity instance corresponds to a row in that table.
public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column()
    private String title;

    @Column()
    private String desc;

    @Column()
    private String link;

    @Column()
    private int maxPoints;

    @Column()
    private boolean quiz;

    @Column()
    private int jointId;

    /* HashMap is used to store JSON for daily "stats"
    "stats": {
        "2022-11-13": {
            "calories": 2200,
            "steps": 8000
        }
    }
    */
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<String,Map<String, Object>> submissions = new HashMap<>(); 

    public Assignment(String title, String desc, String link, int maxPoints, int jointId) {
        this.title = title;
        this.desc = desc;
        this.link = link;
        this.maxPoints = maxPoints;
        this.quiz = false;
        this.jointId = jointId;
        //this.id = id;
    }
    // by SK
    
    public static Assignment[] init() {
        // basics of class construction
        Assignment p1 = new Assignment("Assignment1","Assignment description","abc",1,1);
        Assignment p2 = new Assignment("Assignment2","Assignment description","abc",1,1);
        Assignment p3 = new Assignment("Assignment3","Assignment description","abc",1,1);
        Assignment p4 = new Assignment("Assignment4","Assignment description","abc",1,1);
        Assignment p5 = new Assignment("JQuery Hacks","Assignment description","abc",1,1);
        
        Assignment student_assignment[] = {p1, p2, p3, p4,p5};
        return(student_assignment);
    }
    public static void main(String[] args) {
        // obtain student_grade from initializer
        Assignment student_assignment[] = init();
        // iterate using "enhanced for loop"
        for( Assignment test : student_assignment) {
            System.out.println(test);  // print object
        }
    }
}
