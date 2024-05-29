package com.nighthawk.spring_portfolio.mvc.intern;

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
public class InternRole {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique=true)
    private String name;

    public InternRole(String name) {
        this.name = name;
    }

    public static InternRole[] init() {
        InternRole intern = new InternRole("ROLE_INTERN");

        InternRole[] initArray = {intern};
        return initArray;
    }
}