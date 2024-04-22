package com.nighthawk.spring_portfolio.mvc.linkr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class InternshipService {

    private static InternshipRepository InternshipRepository;

    @Autowired
    public InternshipService(InternshipRepository InternshipRepository) {
        InternshipService.InternshipRepository = InternshipRepository;
    }

    // Method to retrieve all companies
    public static List<Internship> getAllCompanies() {
        return InternshipRepository.findAll();
    }    

    // Method to retrieve a Internship by its ID
    public Optional<Internship> getInternshipById(Long InternshipId) {
        return InternshipRepository.findById(InternshipId);
    }

    // Method to create a new Internship
    public Internship createInternship(Internship Internship) {
        // If the Internship ID is not provided, generate a new ID
        if(Internship.getId() == null){
            Internship.setId(generateNextId());
        }
        return InternshipRepository.save(Internship); // Save the Internship and return it
    }

    // Method to delete a Internship by its ID
    public static void deleteInternship(Long InternshipId) {
        InternshipRepository.deleteById(InternshipId);
    }

    // Method to generate the next ID for a new Internship
    private Long generateNextId() {
        // Generate the next ID by retrieving the maximum ID and incrementing it
        // Note: This approach might not be secure in a concurrent environment and should be improved
        return InternshipRepository.getMaxId() + 1; 
    }
}