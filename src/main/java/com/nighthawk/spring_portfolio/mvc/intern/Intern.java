package com.nighthawk.spring_portfolio.mvc.intern;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.mongodb.lang.NonNull;
import com.nighthawk.spring_portfolio.mvc.person.Person;
import com.nighthawk.spring_portfolio.mvc.person.PersonRole;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Intern {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    
    @NotEmpty
    @Size(min=5)
    @Column(unique=true)
    @Email
    private String email;

    @NotEmpty
    private String password;

    @NonNull
    @Size(min = 2, max = 30, message = "Name (2 to 30 chars)")
    private String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dob;
    
    @NotEmpty
    private String companyName;

    @NotEmpty
    private String industry;

    public Intern(String email, String password, String name, Date dob, String companyName, String industry) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.dob = dob;
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

    public int getAge() {
        if (getDob() != null) {
            long ageInMillis = new Date().getTime() - getDob().getTime();
            return (int) (ageInMillis / 1000 / 60 / 60 / 24 / 365);
        }
        return -1; // Return default value if dob is null
    }

    public PersonRole[] getRoles() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getRoles'");
    }

    public void setRoles(List<PersonRole> updatedRoles) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setRoles'");
    }
}