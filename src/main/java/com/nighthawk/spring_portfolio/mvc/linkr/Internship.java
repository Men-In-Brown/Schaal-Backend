package com.nighthawk.spring_portfolio.mvc.linkr;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.HashSet;
import java.util.Set;
import com.nighthawk.spring_portfolio.mvc.person.Person;
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
    private Set<Person> students = new HashSet<>();

    public Internship(String name, String location, String industry, String ceo, int foundedYear, String description, String website) {
        this.name = name;
        this.location = location;
        this.industry = industry;
        this.foundedYear = foundedYear;
        this.ceo = ceo;
        this.size = 0;
        this.description = description;
        this.website = website;
    }

    public static Internship[] internshipInit() {
        Internship c1 = new Internship("Google", "Mountain View, CA", "Technology", "Sundar Pichai", 1998, "Google is a multinational technology company specializing in internet-related services and products.", "https://www.google.com");
        Internship c2 = new Internship("Microsoft", "Redmond, WA", "Technology", "Satya Nadella", 1975, "Microsoft is a multinational technology company producing computer software, consumer electronics, personal computers, and related services.", "https://www.microsoft.com");
        Internship c3 = new Internship("Apple", "Cupertino, CA", "Technology", "Tim Cook", 1976, "Apple designs, manufactures, and markets mobile communication and media devices, personal computers, and portable digital music players.", "https://www.apple.com");
        Internship c4 = new Internship("Amazon", "Seattle, WA", "E-commerce", "Andy Jassy", 1994, "Amazon is a multinational technology company focusing on e-commerce, cloud computing, digital streaming, and artificial intelligence.", "https://www.amazon.com");
        Internship c5 = new Internship("Facebook", "Menlo Park, CA", "Social Media", "Mark Zuckerberg", 2004, "Facebook is a social media platform that enables users to connect, share, discover, and communicate.", "https://www.facebook.com");
        Internship c6 = new Internship("Tesla", "Palo Alto, CA", "Automotive", "Elon Musk", 2003, "Tesla is a company that specializes in electric vehicles, battery energy storage, and renewable energy solutions.", "https://www.tesla.com");
        Internship c7 = new Internship("Netflix", "Los Gatos, CA", "Entertainment", "Ted Sarandos", 1997, "Netflix is a streaming service that offers a wide variety of award-winning TV shows, movies, anime, documentaries, and more.", "https://www.netflix.com");
        Internship c8 = new Internship("Goldman Sachs", "New York, NY", "Finance", "David Solomon", 1869, "Goldman Sachs is a leading global investment banking, securities, and investment management firm.", "https://www.goldmansachs.com");
        Internship c9 = new Internship("JPMorgan Chase", "New York, NY", "Finance", "Jamie Dimon", 1799, "JPMorgan Chase is a global financial services firm offering solutions in investment banking, financial services, and asset management.", "https://www.jpmorganchase.com");
        Internship c10 = new Internship("Pfizer", "New York, NY", "Pharmaceuticals", "Albert Bourla", 1849, "Pfizer is a leading research-based biopharmaceutical company.", "https://www.pfizer.com");
        Internship c11 = new Internship("Johnson & Johnson", "New Brunswick, NJ", "Healthcare", "Joaquin Duato", 1886, "Johnson & Johnson is a multinational corporation that develops medical devices, pharmaceuticals, and consumer packaged goods.", "https://www.jnj.com");
        Internship c12 = new Internship("Procter & Gamble", "Cincinnati, OH", "Consumer Goods", "Jon R. Moeller", 1837, "Procter & Gamble is a multinational consumer goods corporation specializing in a wide range of personal health/consumer health, and hygiene products.", "https://www.pg.com");
        Internship c13 = new Internship("Coca-Cola", "Atlanta, GA", "Beverages", "James Quincey", 1892, "The Coca-Cola Company is a total beverage company offering over 500 brands in more than 200 countries.", "https://www.coca-colacompany.com");
        Internship c14 = new Internship("Nike", "Beaverton, OR", "Apparel", "John Donahoe", 1964, "Nike is a multinational corporation that designs, manufactures, and sells footwear, apparel, equipment, and accessories.", "https://www.nike.com");
        Internship c15 = new Internship("Intel", "Santa Clara, CA", "Semiconductors", "Pat Gelsinger", 1968, "Intel is a multinational corporation and technology company that designs and manufactures microprocessors and other semiconductor components.", "https://www.intel.com");
        Internship c16 = new Internship("IBM", "Armonk, NY", "Technology", "Arvind Krishna", 1911, "IBM is a global technology company providing hardware, software, cloud-based services, and cognitive computing.", "https://www.ibm.com");
        Internship c17 = new Internship("Oracle", "Austin, TX", "Technology", "Safra Catz", 1977, "Oracle is a multinational computer technology corporation selling database software and technology, cloud engineered systems, and enterprise software products.", "https://www.oracle.com");
        Internship c18 = new Internship("Salesforce", "San Francisco, CA", "Technology", "Marc Benioff", 1999, "Salesforce is a cloud-based software company specializing in customer relationship management.", "https://www.salesforce.com");
        Internship c19 = new Internship("Adobe", "San Jose, CA", "Software", "Shantanu Narayen", 1982, "Adobe is a multinational computer software company producing multimedia and creativity software products.", "https://www.adobe.com");
        Internship c20 = new Internship("Twitter", "San Francisco, CA", "Social Media", "Parag Agrawal", 2006, "Twitter is a social media platform that allows users to post and interact with messages known as tweets.", "https://www.twitter.com");

        return new Internship[]{c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11, c12, c13, c14, c15, c16, c17, c18, c19, c20};
    }
}