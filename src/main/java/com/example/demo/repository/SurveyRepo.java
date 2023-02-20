package com.example.demo.repository;

import com.example.demo.model.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SurveyRepo extends JpaRepository<Survey, Long> {
    Optional<Survey> findByName(String name);
}
