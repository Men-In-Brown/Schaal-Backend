package com.nighthawk.spring_portfolio.mvc.person;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employer extends Person {

    private String companyName;
    private String industry;

    public Employer(String email, String password, String name, Date dob, String companyName, String industry) {
        super(email, password, name, dob);
        this.companyName = companyName;
        this.industry = industry;
    }

    public static Employer[] init() {
        Employer e1 = new Employer();
        e1.setEmail("employer1@gmail.com");
        e1.setPassword("123employer");
        e1.setName("ABC Corporation");
        e1.setDob(new Date());
        e1.setCompanyName("ABC Corporation");
        e1.setIndustry("Technology");

        Employer e2 = new Employer();
        e2.setEmail("employer2@gmail.com");
        e2.setPassword("123employer");
        e2.setName("XYZ Enterprises");
        e2.setDob(new Date());
        e2.setCompanyName("XYZ Enterprises");
        e2.setIndustry("Finance");

        return new Employer[]{e1, e2};
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