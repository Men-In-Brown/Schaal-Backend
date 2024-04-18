package com.nighthawk.spring_portfolio.mvc.chat;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import jakarta.transaction.Transactional;
public interface ChatJpaRepository extends CrudRepository<Chat, Long>{

    List<Chat> findByEmail(String email);
    List<Chat> findByEmailAndFromEmailAndReadFlagOrderByDateSent(String email, String fromEmail, boolean readFlag);
    @Query("SELECT c FROM chat c WHERE (c.email= :email and c.fromEmail = :fromEmail) OR (c.fromEmail= :email and c.email = :fromEmail) ORDER BY c.dateSent ASC")
    List<Chat> findByEmailAndFromEmailOrFromEmailAndEmailOrderByDateSent(@Param("email") String email, @Param("fromEmail")String fromEmail);
    @Transactional
    @Modifying
    @Query("DELETE FROM chat c WHERE (c.email= :email) ")
    void deleteAllByEmail(@Param("email") String email);
    @Transactional
    @Modifying
    @Query("DELETE FROM chat c WHERE (c.email= :email and c.fromEmail = :fromEmail) OR (c.fromEmail= :email and c.email = :fromEmail) ")
    void deleteAllByEmailAndFromEmail(@Param("email") String email, @Param("fromEmail")String fromEmail);
    
}
