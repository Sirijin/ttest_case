package com.example.demo.repository;

import com.example.demo.model.Survey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SurveyRepo extends JpaRepository<Survey, Long> {

    Optional<Survey> findByName(String name);
}
