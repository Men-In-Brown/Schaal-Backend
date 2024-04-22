package com.nighthawk.spring_portfolio.mvc.linkr;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface InternshipRepository extends JpaRepository<Internship, Long> {
    // Repository interface for performing CRUD operations on the Internship entity,
    // including custom queries for specific data retrieval needs.
    List<Internship> findAll();

    List<Internship> findAllById(long i);

    @Query(value = "SELECT coalesce(max(id), 0) FROM Internship")
     Long getMaxId();

    List<Internship> findInternshipByNameIgnoreCase(String name);
}