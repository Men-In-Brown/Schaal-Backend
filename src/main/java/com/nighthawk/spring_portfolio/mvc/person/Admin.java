package com.nighthawk.spring_portfolio.mvc.person;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Admin")
public class Admin extends Person {

    private String role;

    public Admin(String email, String password, String name, Date dob, String role) {
        super(email, password, name, dob);
        this.role = role;
    }

    public static Admin[] init() {
        Admin a1 = new Admin();
        a1.setEmail("tanaypatel327@gmail.com");
        a1.setPassword("tanay123");
        a1.setName("Tanay Patel");
        a1.setDob(new Date());
        a1.setRole("ROLE_ADMIN");

        Admin a2 = new Admin();
        a2.setEmail("varalu@gmail.com");
        a2.setPassword("varalu123");
        a2.setName("Varaprasad Nibhanupudi");
        a2.setDob(new Date());
        a2.setRole("ROLE_ADMIN");

        return new Admin[]{a1, a2};
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