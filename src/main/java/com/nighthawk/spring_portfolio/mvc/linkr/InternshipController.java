package com.nighthawk.spring_portfolio.mvc.linkr;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;

// Using lombok to automatically generate a logger
@Slf4j
@RestController
@RequestMapping("/api/internships") // Base URL for all endpoints in this controller
@CrossOrigin(origins = "*") // Allowing cross-origin requests from any origin
public class InternshipController {

    private final InternshipService InternshipService; // Service to handle business logic
    private final ModelMapper modelMapper; // For entity-to-DTO mapping

    @Autowired
    public InternshipController(InternshipService internshipService, ModelMapper modelMapper) {
        this.InternshipService = internshipService;
        this.modelMapper = modelMapper;
    }

    // Endpoint to get all companies
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<InternshipDTO>> getAllCompanies() {
        List<Internship> companies = com.nighthawk.spring_portfolio.mvc.linkr.InternshipService.getAllCompanies(); // Retrieve companies from the service
        List<InternshipDTO> internshipDTOs = companies.stream()
                .map(internship -> modelMapper.map(internship, InternshipDTO.class)) // Map entities to DTOs
                .collect(Collectors.toList()); // Collect DTOs into a list
        return new ResponseEntity<>(internshipDTOs, HttpStatus.OK); // Return DTO list with OK status
    }

    // Endpoint to get a internship by its ID
    @GetMapping("/{internshipId}")
    public ResponseEntity<Internship> getinternshipById(@PathVariable Long internshipId) {
        log.info("Attempting to retrieve internship with ID: {}", internshipId); // Log the attempt
        Optional<Internship> internship = InternshipService.getInternshipById(internshipId); // Retrieve the internship by ID
        if (internship.isPresent()) { // If internship is found
            log.info("Found internship with ID: {}", internshipId); // Log successful retrieval
            return ResponseEntity.ok().body(internship.get()); // Return internship with OK status
        } else { // If internship is not found
            log.warn("internship with ID {} not found", internshipId); // Log warning for not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Return NOT_FOUND status
        }
    }

    // Endpoint to add a new internship
    @PostMapping
    @PostAuthorize("hasRole('ADMIN') or hasRole('EMPLOYER')")
    public ResponseEntity<Internship> addinternship(@RequestBody Internship internship) {
        log.info("Attempting to add internship: {}", internship); // Log the attempt
        Internship addedinternship = InternshipService.createInternship(internship); // Create the internship
        log.info("internship added successfully: {}", addedinternship); // Log successful addition
        return new ResponseEntity<>(addedinternship, HttpStatus.CREATED); // Return the added internship with CREATED status
    }

    // Endpoint to delete a internship by its ID
    @DeleteMapping("/{internshipId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYER')")
    public ResponseEntity<Void> deleteinternship(@PathVariable Long internshipId) {
        System.out.println("Attempting to delete internship with ID: " + internshipId); // Log the attempt
        com.nighthawk.spring_portfolio.mvc.linkr.InternshipService.deleteInternship(internshipId); // Delete the internship
        System.out.println("internship with ID {} deleted successfully" + internshipId); // Log successful deletion
        return ResponseEntity.noContent().build(); // Return NO_CONTENT status
    }
}