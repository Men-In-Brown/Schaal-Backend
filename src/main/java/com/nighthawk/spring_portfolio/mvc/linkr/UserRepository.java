package com.nighthawk.spring_portfolio.mvc.linkr;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAllById(Long id);

    @Query(value = "SELECT coalesce(max(id), 0) FROM User")
     Long getMaxId();

    User findByEmail(String email);

    List<User> findAllByEmail(String email);
}