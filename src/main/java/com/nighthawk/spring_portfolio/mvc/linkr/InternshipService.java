package com.nighthawk.spring_portfolio.mvc.linkr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class InternshipService {

    private final InternshipRepository internshipRepository;

    @Autowired
    public InternshipService(InternshipRepository internshipRepository) {
        this.internshipRepository = internshipRepository;
    }

    public List<Internship> getAllInternships() {
        return internshipRepository.findAll();
    }

    public Optional<Internship> getInternshipById(Long internshipId) {
        return internshipRepository.findById(internshipId);
    }

    public Internship createInternship(Internship internship) {
        return internshipRepository.save(internship);
    }

    public void deleteInternship(Long internshipId) {
        internshipRepository.deleteById(internshipId);
    }
}