package com.nighthawk.spring_portfolio.mvc.linkrAuthentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pat")
public class PATApiController {

    @Autowired
    private PatJpaRepository patJpaRepository;

    // Get all PATs
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping
    public List<LinkrPAT> getAllPATs() {
        return patJpaRepository.findAll();
    }

    // Get PAT by ID
    @GetMapping("/{user}")
    public ResponseEntity<List<LinkrPAT>> getPATById(@PathVariable String user) {
        List<LinkrPAT> pat = patJpaRepository.findAllByUser(user);
        return ResponseEntity.ok(pat);
    }

    @PostMapping("/create")
    public ResponseEntity<LinkrPAT> createPAT(@RequestParam("user") String user){
        LinkrPAT l = new LinkrPAT(user);
        patJpaRepository.save(l);
        return ResponseEntity.ok(l);
    }
}