package com.macrochallenge.backend.repositories;

import com.macrochallenge.backend.model.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TestRepository extends JpaRepository<Test, Integer> {
    Optional<Test> findByNameAndYear(String name, String year);
}
