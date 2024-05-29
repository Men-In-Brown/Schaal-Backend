package com.nighthawk.spring_portfolio.mvc.teacher;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRoleJpaRepository extends JpaRepository<TeacherRole, Long> {
    TeacherRole findByName(String name);
}
