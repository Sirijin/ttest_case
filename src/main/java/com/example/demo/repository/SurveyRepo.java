package com.example.demo.repository;

import com.example.demo.model.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SurveyRepo extends JpaRepository<Survey, Integer> {
    Optional<Survey> findByName(String name);

//    @Modifying
//    @Query("update Survey s set s.active = false where s.endDate < current_timestamp")
//    void updateActiveState();
}
