package com.nighthawk.spring_portfolio.mvc.admin;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRoleJpaRepository extends JpaRepository<AdminRole, Long> {
    AdminRole findByName(String name);
}
