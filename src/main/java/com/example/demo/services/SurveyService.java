package com.example.demo.services;

import com.example.demo.repositories.SurveyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class SurveyService {
    private final SurveyRepo surveyRepo;

    @Autowired
    public SurveyService(SurveyRepo surveyRepo) {
        this.surveyRepo = surveyRepo;
    }


}
