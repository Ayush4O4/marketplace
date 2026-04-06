package com.Projects.marketplace.repositories;

import com.Projects.marketplace.entity.College;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CollegeRepositoy extends JpaRepository<College,Long> {

    Optional<College> findByDomain(String domain);
}
