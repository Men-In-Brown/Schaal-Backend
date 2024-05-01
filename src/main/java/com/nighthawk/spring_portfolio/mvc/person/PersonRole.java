package com.nighthawk.spring_portfolio.mvc.person;

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
public class PersonRole {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique=true)
    private String name;

    public PersonRole(String name) {
        this.name = name;
    }

    public static PersonRole[] init() {
        PersonRole student = new PersonRole("ROLE_STUDENT");
        PersonRole teacher = new PersonRole("ROLE_TEACHER");
        PersonRole employer = new PersonRole("ROLE_EMPLOYER");
        PersonRole admin = new PersonRole("ROLE_ADMIN");

        PersonRole[] initArray = {student, teacher, admin, employer};
        return initArray;
    }
}