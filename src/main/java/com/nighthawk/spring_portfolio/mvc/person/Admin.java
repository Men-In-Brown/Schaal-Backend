package com.nighthawk.spring_portfolio.mvc.person;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Admin extends Person {

    private String role;

    public Admin(String email, String password, String name, Date dob, String role) {
        super(email, password, name, dob);
        this.role = role;
    }

    public static Admin[] init() {
        Admin a1 = new Admin();
        a1.setEmail("admin1@gmail.com");
        a1.setPassword("123admin");
        a1.setName("Admin User");
        a1.setDob(new Date());
        a1.setRole("System Administrator");

        Admin a2 = new Admin();
        a2.setEmail("admin2@gmail.com");
        a2.setPassword("123admin");
        a2.setName("Super Admin");
        a2.setDob(new Date());
        a2.setRole("Superuser");

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