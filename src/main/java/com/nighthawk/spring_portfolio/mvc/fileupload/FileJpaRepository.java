package com.nighthawk.spring_portfolio.mvc.fileupload;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FileJpaRepository extends JpaRepository<File, Long> {
    File findByFileName(String fileName);
    List<File> findAllByOrderByFileNameAsc();
    List<File> findByFileNameIgnoreCase(String fileName);
    
    @Query(
            value = "SELECT * FROM File p WHERE p.fileName LIKE ?1",
            nativeQuery = true)
    List<File> findByLikeTermNative(String term);
}
