package com.example.demo.repositories;

import com.example.demo.models.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepo extends JpaRepository<Question, Integer> {

}
