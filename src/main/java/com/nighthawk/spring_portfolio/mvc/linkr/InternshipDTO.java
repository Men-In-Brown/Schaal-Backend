package com.nighthawk.spring_portfolio.mvc.linkr;

// Data Transfer Object (DTO) representing a Internship entity,
// used for transferring data between the front-end and back-end layers.

public class InternshipDTO {
    private Long id;
    private String name;
    private String mission;
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

    public InternshipDTO(Long id, String name, String mission, String location, String industry, int size, String description, String website, int foundedYear, String ceo) {
        this.id = id;
        this.name = name;
        this.mission = mission;
        this.location = location;
        this.industry = industry;
        this.size = size;
        this.description = description;
        this.website = website;
        this.foundedYear = foundedYear;
        this.ceo = ceo;
    }

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

    public String getMission() {
        return mission;
    }

    public void setMission(String mission) {
        this.mission = mission;
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