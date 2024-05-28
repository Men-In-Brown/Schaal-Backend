package com.nighthawk.spring_portfolio.mvc.intern;

import java.util.Date;

import com.nighthawk.spring_portfolio.mvc.person.Person;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Intern extends Person {

    private String companyName;
    private String industry;

    public Intern(String email, String password, String name, Date dob, String companyName, String industry) {
        super(email, password, name, dob);
        this.companyName = companyName;
        this.industry = industry;
    }

    public static Intern[] init() {
        Intern e1 = new Intern();
        e1.setEmail("employer1@gmail.com");
        e1.setPassword("123employer");
        e1.setName("ABC Corporation");
        e1.setDob(new Date());
        e1.setCompanyName("ABC Corporation");
        e1.setIndustry("Technology");

        Intern e2 = new Intern();
        e2.setEmail("employer2@gmail.com");
        e2.setPassword("123employer");
        e2.setName("XYZ Enterprises");
        e2.setDob(new Date());
        e2.setCompanyName("XYZ Enterprises");
        e2.setIndustry("Finance");

        return new Intern[]{e1, e2};
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