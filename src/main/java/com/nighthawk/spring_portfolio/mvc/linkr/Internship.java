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

    public Internship(String name, String location, String industry, String ceo) {
        this.name = name;
        this.location = location;
        this.industry = industry;
        this.foundedYear = Year.now().getValue();
        this.ceo = ceo;
        this.size = 0;
        this.description = null;
        this.website = null;
    }

    public static Internship[] internshipInit() {
        Internship c1 = new Internship("Google", "Mountain View, CA", "Technology", "Sundar Pichai");
        Internship c2 = new Internship("Microsoft", "Redmond, WA", "Technology", "Satya Nadella");
        Internship c3 = new Internship("Apple", "Cupertino, CA", "Technology", "Tim Cook");
        Internship c4 = new Internship("Amazon", "Seattle, WA", "E-commerce", "Andy Jassy");
        Internship c5 = new Internship("Facebook", "Menlo Park, CA", "Social Media", "Mark Zuckerberg");
        Internship c6 = new Internship("Tesla", "Palo Alto, CA", "Automotive", "Elon Musk");
        Internship c7 = new Internship("Netflix", "Los Gatos, CA", "Entertainment", "Ted Sarandos");
        Internship c8 = new Internship("Goldman Sachs", "New York, NY", "Finance", "David Solomon");
        Internship c9 = new Internship("JPMorgan Chase", "New York, NY", "Finance", "Jamie Dimon");
        Internship c10 = new Internship("Pfizer", "New York, NY", "Pharmaceuticals", "Albert Bourla");
        Internship c11 = new Internship("Johnson & Johnson", "New Brunswick, NJ", "Healthcare", "Joaquin Duato");
        Internship c12 = new Internship("Procter & Gamble", "Cincinnati, OH", "Consumer Goods", "Jon R. Moeller");
        Internship c13 = new Internship("Coca-Cola", "Atlanta, GA", "Beverages", "James Quincey");
        Internship c14 = new Internship("Nike", "Beaverton, OR", "Apparel", "John Donahoe");
        Internship c15 = new Internship("Intel", "Santa Clara, CA", "Semiconductors", "Pat Gelsinger");
        Internship c16 = new Internship("IBM", "Armonk, NY", "Technology", "Arvind Krishna");
        Internship c17 = new Internship("Oracle", "Austin, TX", "Technology", "Safra Catz");
        Internship c18 = new Internship("Salesforce", "San Francisco, CA", "Technology", "Marc Benioff");
        Internship c19 = new Internship("Adobe", "San Jose, CA", "Software", "Shantanu Narayen");
        Internship c20 = new Internship("Twitter", "San Francisco, CA", "Social Media", "Parag Agrawal");

        return new Internship[]{c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11, c12, c13, c14, c15, c16, c17, c18, c19, c20};
    }
}