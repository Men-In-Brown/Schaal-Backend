package com.nighthawk.spring_portfolio.mvc.person;

public class PersonRequest {
    private String email;
    private String password;
    private String name;
    private String dob;
    private String role;

    // Constructors
    public PersonRequest() {
    }

    public PersonRequest(String email, String password, String name, String dob, String role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.dob = dob;
        this.role = role;
    }

    // Getters and setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    // toString() method (optional, for debugging)
    @Override
    public String toString() {
        return "PersonRequest{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", dob='" + dob + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
