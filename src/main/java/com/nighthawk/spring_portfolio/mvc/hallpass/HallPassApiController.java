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

    @GetMapping
    public List<HallPass> getAllHallPasses() {
        return hallPassJpaRepository.findAll();
    }

    @GetMapping("/status/{status}")
    public List<HallPass> getAllHallPassesByStatus(@PathVariable String status) {
        return hallPassJpaRepository.findByStatus(status);
    }

    @GetMapping("/student/{studentId}")
    public List<HallPass> getAllHallPassesForStudent(@PathVariable String studentId) {
        return hallPassJpaRepository.findByStudentId(studentId);
    }

    @GetMapping("/teacher/{teacherId}")
    public List<HallPass> getAllHallPassesForTeacher(@PathVariable String teacherId) {
        return hallPassJpaRepository.findByTeacherId(teacherId);
    }

    @GetMapping("/teacher/{teacherId}/status/{status}")
    public List<HallPass> getAllHallPassesForTeacherByStatus(@PathVariable String teacherId,@PathVariable String status) {
        if (status.equalsIgnoreCase("All")){
            return hallPassJpaRepository.findByTeacherId(teacherId);    
        }
        return hallPassJpaRepository.findByTeacherIdAndStatus(teacherId, status);
    }

    @GetMapping("/{id}")
    public HallPass getHallPassById(@PathVariable Long id) {
        Optional<HallPass> hallPass = hallPassJpaRepository.findById(id);
        return hallPass.orElse(null);
    }

    @PostMapping
    public HallPass createHallPass(@RequestBody HallPass hallPass) {
        hallPass.setStatus("Pending");  // Set initial status to Pending
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
            existingHallPass.setStatus(updatedHallPass.getStatus());
            return hallPassJpaRepository.save(existingHallPass);
        }
        return null;
    }

    @PutMapping("/{id}/{status}")
    public HallPass updateHallPassStatus(@PathVariable Long id, @PathVariable String status) {
        Optional<HallPass> hallPass = hallPassJpaRepository.findById(id);
        if (hallPass.isPresent()) {
            HallPass existingHallPass = hallPass.get();
            existingHallPass.setStatus(status);
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

