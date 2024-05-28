package com.nighthawk.spring_portfolio.mvc.student;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nighthawk.spring_portfolio.mvc.linkr.Internship;
import com.nighthawk.spring_portfolio.mvc.person.PersonRole;

public class StudentRequest {

    private Long id;
    private String email;
    private String password;
    private String name;
    private Date dob;
    private Collection<PersonRole> roles;
    private Map<String, Map<String, Object>> stats;
    private int grade;
    private String interest;
    private List<Internship> internships;
    private Map<String, String> classes;

    public StudentRequest() {
        this.roles = new ArrayList<>();
        this.stats = new HashMap<>();
        this.internships = new ArrayList<>();
        this.classes = new HashMap<>();
    }

    public StudentRequest(Long id, String email, String password, String name, Date dob, Collection<PersonRole> roles,
                      Map<String, Map<String, Object>> stats, int grade, String interest, List<Internship> internships,
                      Map<String, String> classes) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.dob = dob;
        this.roles = roles;
        this.stats = stats;
        this.grade = grade;
        this.interest = interest;
        this.internships = internships;
        this.classes = classes;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public Collection<PersonRole> getRoles() {
        return roles;
    }

    public void setRoles(Collection<PersonRole> roles) {
        this.roles = roles;
    }

    public Map<String, Map<String, Object>> getStats() {
        return stats;
    }

    public void setStats(Map<String, Map<String, Object>> stats) {
        this.stats = stats;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public List<Internship> getInternships() {
        return internships;
    }

    public void setInternships(List<Internship> internships) {
        this.internships = internships;
    }

    public Map<String, String> getClasses() {
        return classes;
    }

    public void setClasses(Map<String, String> classes) {
        this.classes = classes;
    }
}
