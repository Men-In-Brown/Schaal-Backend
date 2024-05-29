package com.nighthawk.spring_portfolio.mvc.teacher;

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
public class Teacher {
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
    private String subject;

    public Teacher(String email, String password, String name, Date dob, String subject) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.dob = dob;
        this.subject = subject;
    }

    public static Teacher[] init() {
        Teacher t1 = new Teacher();
        t1.setEmail("teacher1@gmail.com");
        t1.setPassword("123teacher");
        t1.setName("Mr. Smith");
        t1.setDob(new Date());
        t1.setSubject("Mathematics");

        Teacher t2 = new Teacher();
        t2.setEmail("teacher2@gmail.com");
        t2.setPassword("123teacher");
        t2.setName("Ms. Johnson");
        t2.setDob(new Date());
        t2.setSubject("Science");

        return new Teacher[]{t1, t2};
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