package com.nighthawk.spring_portfolio.mvc.person;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AdminJpaRepository extends JpaRepository<Admin, Long> {
    Admin findByEmail(String email);
    PersonRole findByName(String name);

    List<Admin> findAllByOrderByNameAsc();

    List<Admin> findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(String name, String email);

    Admin findByEmailAndPassword(String email, String password);

    @Query(
            value = "SELECT * FROM Person p WHERE p.name LIKE ?1 or p.email LIKE ?1",
            nativeQuery = true)
    List<Admin> findByLikeTermNative(String term);
    void save(PersonRole role);
}
