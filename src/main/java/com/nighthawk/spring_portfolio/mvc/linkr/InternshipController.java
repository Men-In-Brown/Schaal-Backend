package com.nighthawk.spring_portfolio.mvc.linkr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.nighthawk.hacks.InternshipSearcher;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/internships")
@CrossOrigin(origins = "*")
public class InternshipController {

    private final InternshipService internshipService;

    @Autowired
    public InternshipController(InternshipService internshipService) {
        this.internshipService = internshipService;
    }

    @GetMapping
    public ResponseEntity<List<InternshipDTO>> getAllInternships() {
        List<Internship> internships = internshipService.getAllInternships();
        List<InternshipDTO> internshipDTOs = internships.stream()
                .map(internship -> new InternshipDTO(internship))
                .collect(Collectors.toList());
        return new ResponseEntity<>(internshipDTOs, HttpStatus.OK);
    }

    @GetMapping("/{internshipId}")
    public ResponseEntity<InternshipDTO> getInternshipById(@PathVariable int internshipId) {
        Optional<Internship> internship = internshipService.getInternshipById((long) internshipId);
        if (internship.isPresent()) {
            InternshipDTO internshipDTO = new InternshipDTO(internship.get());
            return ResponseEntity.ok().body(internshipDTO); 
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<InternshipDTO>> getSearchedInternships(@RequestParam String query) {
        List<Internship> internships = internshipService.getAllInternships();
        List<InternshipDTO> internshipDTOs = internships.stream()
                .map(internship -> new InternshipDTO(internship))
                .collect(Collectors.toList());
        List<InternshipDTO> searched = InternshipSearcher.searchInternships(internshipDTOs, query);
        return new ResponseEntity<>(searched, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<InternshipDTO> addInternship(@RequestBody InternshipDTO internshipDTO) {
        Internship internship = new Internship(
                internshipDTO.getName(),
                internshipDTO.getLocation(),
                internshipDTO.getIndustry(),
                internshipDTO.getCeo(), 
                internshipDTO.getFoundedYear(), 
                internshipDTO.getDescription(), 
                internshipDTO.getWebsite()
        );
        Internship addedInternship = internshipService.createInternship(internship);
        InternshipDTO addedInternshipDTO = new InternshipDTO(addedInternship);
        return new ResponseEntity<>(addedInternshipDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/{internshipId}")
    public ResponseEntity<Void> deleteInternship(@PathVariable Long internshipId) {
        internshipService.deleteInternship(internshipId);
        return ResponseEntity.noContent().build();
    }
}
