package com.nighthawk.spring_portfolio.mvc.linkr;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Year;
import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "internship")
@NoArgsConstructor
public class Internship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String location;
    private String industry;
    private int size;
    private String description;
    private String website;
    private int foundedYear;
    private String ceo;

    @OneToMany(mappedBy = "internship", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<User> students = new HashSet<>();

    public Internship(String name, String location, String industry, String ceo, String description, String website) {
        this.name = name;
        this.location = location;
        this.industry = industry;
        this.foundedYear = Year.now().getValue();
        this.ceo = ceo;
        this.size = 0;
        this.description = description;
        this.website = website;
    }

    public static Internship[] internshipInit() {
        Internship c1 = new Internship("Name 1", "California", "Tech", "None", "heehee", "heehee");
        Internship c2 = new Internship("Name 2", "India", "Hospitality", "Tanay", "heehee", "heehee");
        Internship c3 = new Internship("Name 3", "Shanghai", "Tax Fraud", "Paaras", "heehee", "heehee");
        return new Internship[]{c1, c2, c3};
    }
}