package com.nighthawk.spring_portfolio.mvc.linkrAuthentication;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.vladmihalcea.hibernate.type.array.DateArrayType;

@Repository
public interface PatJpaRepository extends JpaRepository<LinkrPAT, Date> {


    List<LinkrPAT> findAllByUser(String user);
    List<LinkrPAT> findAll();

}