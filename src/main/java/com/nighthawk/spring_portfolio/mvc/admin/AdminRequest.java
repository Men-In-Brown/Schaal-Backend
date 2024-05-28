package com.nighthawk.spring_portfolio.mvc.admin;

import java.util.Date;
import java.util.Map;
import java.util.Collection;
import com.nighthawk.spring_portfolio.mvc.person.PersonRole;

public class AdminRequest {

    private Long id;
    private String email;
    private String password;
    private String name;
    private Date dob;
    private Collection<PersonRole> roles;
    private Map<String, Map<String, Object>> stats;
    private String role;

    // No-args constructor
    public AdminRequest() {}

    // All-args constructor
    public AdminRequest(Long id, String email, String password, String name, Date dob, Collection<PersonRole> roles, Map<String, Map<String, Object>> stats, String role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.dob = dob;
        this.roles = roles;
        this.stats = stats;
        this.role = role;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "AdminDTO{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", dob=" + dob +
                ", roles=" + roles +
                ", stats=" + stats +
                ", role='" + role + '\'' +
                '}';
    }
}
