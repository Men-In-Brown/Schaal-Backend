package com.nighthawk.spring_portfolio.mvc.student;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class StudentRole {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique=true)
    private String name;

    public StudentRole(String name) {
        this.name = name;
    }

    public static StudentRole[] init() {
        StudentRole admin = new StudentRole("ROLE_STUDENT");

        StudentRole[] initArray = {admin};
        return initArray;
    }
}