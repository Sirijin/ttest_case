package com.example.demo.repositories;

import com.example.demo.models.Survey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyRepo extends JpaRepository<Survey, Integer> {

}
