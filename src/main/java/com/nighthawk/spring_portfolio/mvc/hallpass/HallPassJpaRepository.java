package com.nighthawk.spring_portfolio.mvc.hallpass;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface HallPassJpaRepository extends JpaRepository<HallPass, Long> {
    List<HallPass> findByStatus(String status);
    List<HallPass> findByStudentId(String studentId);
    List<HallPass> findByTeacherId(String teacherId);
    List<HallPass> findByTeacherIdAndStatus(String teacherId, String status);
}
