package com.nighthawk.spring_portfolio.mvc.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nighthawk.spring_portfolio.mvc.person.PersonRole;

import java.util.List;

public interface StudentJpaRepository extends JpaRepository<Student, Long> {
    Student findByEmail(String email);
    PersonRole findByName(String name);

    List<Student> findAllByOrderByNameAsc();

    List<Student> findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(String name, String email);

    Student findByEmailAndPassword(String email, String password);

    @Query(
            value = "SELECT * FROM Person p WHERE p.name LIKE ?1 or p.email LIKE ?1",
            nativeQuery = true)
    List<Student> findByLikeTermNative(String term);
    void save(PersonRole role);
}