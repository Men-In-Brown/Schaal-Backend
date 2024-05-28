package com.nighthawk.spring_portfolio.mvc.teacher;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nighthawk.spring_portfolio.mvc.person.PersonRole;

import java.util.List;

public interface TeacherJpaRepository extends JpaRepository<Teacher, Long> {
    Teacher findByEmail(String email);
    PersonRole findByName(String name);

    List<Teacher> findAllByOrderByNameAsc();

    List<Teacher> findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(String name, String email);

    Teacher findByEmailAndPassword(String email, String password);

    @Query(
            value = "SELECT * FROM Person p WHERE p.name LIKE ?1 or p.email LIKE ?1",
            nativeQuery = true)
    List<Teacher> findByLikeTermNative(String term);
    void save(PersonRole role);
}