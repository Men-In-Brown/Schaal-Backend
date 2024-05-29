package com.nighthawk.spring_portfolio.mvc.teacher;

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
public class TeacherRole {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique=true)
    private String name;

    public TeacherRole(String name) {
        this.name = name;
    }

    public static TeacherRole[] init() {
        TeacherRole admin = new TeacherRole("ROLE_STUDENT");

        TeacherRole[] initArray = {admin};
        return initArray;
    }
}