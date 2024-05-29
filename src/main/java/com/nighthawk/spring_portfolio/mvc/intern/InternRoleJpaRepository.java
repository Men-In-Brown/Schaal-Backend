package com.nighthawk.spring_portfolio.mvc.intern;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InternRoleJpaRepository extends JpaRepository<InternRole, Long> {
    InternRole findByName(String name);
}
