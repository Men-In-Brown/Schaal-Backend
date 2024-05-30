package com.nighthawk.spring_portfolio.mvc.linkrAuthentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<Object> createPAT(@RequestParam("user") String user){
        List<LinkrPAT> pat = patJpaRepository.findAllByUser(user);
        if(pat.size() == 0){
            LinkrPAT l = new LinkrPAT(user);
            patJpaRepository.save(l);
            return ResponseEntity.ok(l);
        }
        else{
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
            .body("Creating PAT for already occurring site");
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Object> deletePAT(@RequestParam("user") String user){
        List<LinkrPAT> pat = patJpaRepository.findAllByUser(user);
        if(pat.size() != 0){
            patJpaRepository.deleteById(pat.get(0).getDate());
            return ResponseEntity.ok(pat.get(0));
        }
        else{
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
            .body("Issue with PAT you are requesting");
        }
    }
}