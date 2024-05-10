package com.nighthawk.spring_portfolio.mvc.hallpass;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/hallpass")
public class HallPassApiController {

    @Autowired
    private HallPassJpaRepository hallPassJpaRepository;

    @GetMapping("/")
    public List<HallPass> getAllHallPasses() {
        return hallPassJpaRepository.findAll();
    }

    @GetMapping("/{id}")
    public HallPass getHallPassById(@PathVariable Long id) {
        Optional<HallPass> hallPass = hallPassJpaRepository.findById(id);
        return hallPass.orElse(null);
    }

    @PostMapping("/")
    public HallPass createHallPass(@RequestBody HallPass hallPass) {
        return hallPassJpaRepository.save(hallPass);
    }

    @PutMapping("/{id}")
    public HallPass updateHallPass(@PathVariable Long id, @RequestBody HallPass updatedHallPass) {
        Optional<HallPass> hallPass = hallPassJpaRepository.findById(id);
        if (hallPass.isPresent()) {
            HallPass existingHallPass = hallPass.get();
            existingHallPass.setStudentId(updatedHallPass.getStudentId());
            existingHallPass.setTeacherId(updatedHallPass.getTeacherId());
            existingHallPass.setReason(updatedHallPass.getReason());
            existingHallPass.setExpiryTime(updatedHallPass.getExpiryTime());
            return hallPassJpaRepository.save(existingHallPass);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public String deleteHallPass(@PathVariable Long id) {
        hallPassJpaRepository.deleteById(id);
        return "Deleted Hall Pass with ID: " + id;
    }
}
