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

    private String name; // user
    private String location; // user
    private String industry; // user
    private int size; // automated
    private String description; // set to null, can be replaced
    private String website; // set to null, can be replaced
    private int foundedYear; // automated
    private String ceo; // automated (student creating internship)
    // private int investments;

    @OneToMany(mappedBy = "internship", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<User> students = new HashSet<>();


    public Internship(String name, String mission, String location, String industry, String ceo){
        // Users Set
        this.name = name;
        this.location = location;
        this.industry = industry;

        // Automated Set
        this.foundedYear = Year.now().getValue();
        this.ceo = ceo;
        this.size = 0;
        
        // null Set
        this.description = null;
        this.website = null;
    }

    public static Internship[] internshipInit(){
        Internship c1 = new Internship("Name 1", "To Name", "California", "Tech", "None");
        Internship c2 = new Internship("Name 2", "To not name", "India", "Hosptality", "Tanay");
        Internship c3 = new Internship("Name 3", "That is the question", "Shanghai", "Tax Fraud", "Paaras");
        Internship[] clist = {c1, c2, c3};
        return clist;
    }
}