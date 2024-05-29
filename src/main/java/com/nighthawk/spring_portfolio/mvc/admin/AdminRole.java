package com.nighthawk.spring_portfolio.mvc.admin;

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
public class AdminRole {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique=true)
    private String name;

    public AdminRole(String name) {
        this.name = name;
    }

    public static AdminRole[] init() {
        AdminRole admin = new AdminRole("ROLE_ADMIN");

        AdminRole[] initArray = {admin};
        return initArray;
    }
}