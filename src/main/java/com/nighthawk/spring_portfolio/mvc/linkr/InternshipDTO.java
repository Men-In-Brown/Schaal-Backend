package com.nighthawk.spring_portfolio.mvc.linkr;

public class InternshipDTO {
    private Long id;
    private String name;
    private String location;
    private String industry;
    private int size;
    private String description;
    private String website;
    private int foundedYear;
    private String ceo;

    public InternshipDTO() {
        // Default constructor
    }

    public InternshipDTO(Internship internship) {
        this.id = internship.getId();
        this.name = internship.getName();
        this.location = internship.getLocation();
        this.industry = internship.getIndustry();
        this.size = internship.getSize();
        this.description = internship.getDescription();
        this.website = internship.getWebsite();
        this.foundedYear = internship.getFoundedYear();
        this.ceo = internship.getCeo();
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public int getFoundedYear() {
        return foundedYear;
    }

    public void setFoundedYear(int foundedYear) {
        this.foundedYear = foundedYear;
    }

    public String getCeo() {
        return ceo;
    }

    public void setCeo(String ceo) {
        this.ceo = ceo;
    }
}