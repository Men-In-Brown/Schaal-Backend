package com.nighthawk.spring_portfolio.mvc.admin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import static jakarta.persistence.FetchType.EAGER;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.format.annotation.DateTimeFormat;

import com.nighthawk.spring_portfolio.mvc.person.PersonRole;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Admin")
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

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

    @ManyToMany(fetch = EAGER)
    private Collection<PersonRole> roles = new ArrayList<>();

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<String,Map<String, Object>> stats = new HashMap<>(); 

    private String role;

    public Admin(String email, String password, String name, Date dob, String role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.dob = dob;
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
}