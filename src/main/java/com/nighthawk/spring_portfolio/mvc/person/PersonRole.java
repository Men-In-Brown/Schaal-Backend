package com.nighthawk.spring_portfolio.mvc.person;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

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
        PersonRole user = new PersonRole("ROLE_USER");
        PersonRole student = new PersonRole("ROLE_STUDENT");
        PersonRole teacher = new PersonRole("ROLE_TEACHER");
        PersonRole intern = new PersonRole("ROLE_INTERN");
        PersonRole admin = new PersonRole("ROLE_ADMIN");
        PersonRole[] initArray = {user, student, teacher, intern, admin};
        return initArray;
    }


}
