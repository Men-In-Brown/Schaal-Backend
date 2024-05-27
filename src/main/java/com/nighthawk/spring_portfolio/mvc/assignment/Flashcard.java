package com.nighthawk.spring_portfolio.mvc.assignment;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Data  // Annotations to simplify writing code (ie constructors, setters)
@NoArgsConstructor
@AllArgsConstructor
@Entity // Annotation to simplify creating an entity, which is a lightweight persistence domain object. Typically, an entity represents a table in a relational database, and each entity instance corresponds to a row in that table.
public class Flashcard {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column()
    private String title;

    @Column()
    private String desc;

    @Column()
    private int maxPoints;

    @Column()
    private int reqCards;

    @Column()
    private String type;

    @Column()
    private int jointId;

    @Column()
    private LocalDateTime due;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<String, String> flashcards; // Key: front, Value: back

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<String, Integer> completions; // Key: username, Value: number of completed flashcards

    public Flashcard(String title, String desc, int maxPoints, int jointId, Map<String, String> flashcards, LocalDateTime due, int reqCards) {
        this.title = title;
        this.desc = desc;
        this.maxPoints = maxPoints;
        this.jointId = jointId;
        this.flashcards = flashcards;
        this.type = "flashcards";
        this.due = due;
        this.reqCards = reqCards;
        this.completions = new HashMap<>();
    }

}
