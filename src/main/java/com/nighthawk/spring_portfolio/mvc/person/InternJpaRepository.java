package com.nighthawk.spring_portfolio.mvc.person;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InternJpaRepository extends JpaRepository<Intern, Long> {
    Intern findByEmail(String email);
    PersonRole findByName(String name);

    List<Intern> findAllByOrderByNameAsc();

    List<Intern> findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(String name, String email);

    Intern findByEmailAndPassword(String email, String password);

    @Query(
            value = "SELECT * FROM Person p WHERE p.name LIKE ?1 or p.email LIKE ?1",
            nativeQuery = true)
    List<Intern> findByLikeTermNative(String term);
    void save(PersonRole role);
}