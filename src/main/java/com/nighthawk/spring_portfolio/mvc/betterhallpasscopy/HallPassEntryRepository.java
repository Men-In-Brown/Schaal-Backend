package com.nighthawk.spring_portfolio.mvc.betterhallpasscopy;

import org.springframework.data.jpa.repository.JpaRepository;
import com.nighthawk.spring_portfolio.mvc.person.Person;

public interface HallPassEntryRepository extends JpaRepository<HallPassEntry, Long> {
    HallPassEntry findByStudent(Person student);
}
