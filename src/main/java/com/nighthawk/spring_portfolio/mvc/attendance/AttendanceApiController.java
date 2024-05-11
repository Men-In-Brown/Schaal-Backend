package com.nighthawk.spring_portfolio.mvc.attendance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceApiController {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @GetMapping
    public List<Attendance> getAllAttendance() {
        return attendanceRepository.findAll();
    }

    @GetMapping("/{id}")
    public Attendance getAttendanceById(@PathVariable Long id) {
        Optional<Attendance> attendance = attendanceRepository.findById(id);
        return attendance.orElse(null);
    }

    @PostMapping
    public Attendance createAttendance(@RequestBody Attendance attendance) {
        return attendanceRepository.save(attendance);
    }

    @PutMapping("/{id}")
    public Attendance updateAttendance(@PathVariable Long id, @RequestBody Attendance updatedAttendance) {
        Optional<Attendance> attendance = attendanceRepository.findById(id);
        if (attendance.isPresent()) {
            Attendance existingAttendance = attendance.get();
            existingAttendance.setStudentId(updatedAttendance.getStudentId());
            existingAttendance.setTeacherId(updatedAttendance.getTeacherId());
            existingAttendance.setClassId(updatedAttendance.getClassId());
            existingAttendance.setStatus(updatedAttendance.getStatus());
            existingAttendance.setTime(updatedAttendance.getTime());
            return attendanceRepository.save(existingAttendance);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public String deleteAttendance(@PathVariable Long id) {
        attendanceRepository.deleteById(id);
        return "Deleted Attendance with ID: " + id;
    }
}
