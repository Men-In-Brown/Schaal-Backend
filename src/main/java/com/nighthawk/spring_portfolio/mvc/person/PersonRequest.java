package com.nighthawk.spring_portfolio.mvc.person;

import java.util.Date;

public class PersonRequest {
    private String email;
    private String password;
    private String name;
    private String usn;
    private Date dob;
    private String[] subjectsOfInterest;

    public PersonRequest(String email, String password, String name, String usn, Date dob, String[] subjectsOfInterest) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.usn = usn;
        this.dob = dob;
        this.subjectsOfInterest = subjectsOfInterest;
    }

    // getters and setters
    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public String getName() {
        return this.name;
    }

    public String getUsn() {
        return this.usn;
    }

    public String[] getSubjectsOfInterest() {
        return this.subjectsOfInterest;
    }

    public Date getDob() {
        // TODO Auto-generated method stub
        return this.dob;
    }
}
