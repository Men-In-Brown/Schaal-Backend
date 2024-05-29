package com.nighthawk.spring_portfolio.mvc.student;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRoleJpaRepository extends JpaRepository<StudentRole, Long> {
    StudentRole findByName(String name);
}
