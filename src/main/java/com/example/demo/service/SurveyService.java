package com.example.demo.service;

import com.example.demo.model.Survey;
import com.example.demo.repository.SurveyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SurveyService {
    private final SurveyRepo surveyRepo;

    @Autowired
    public SurveyService(SurveyRepo surveyRepo) {
        this.surveyRepo = surveyRepo;
    }

    public List<Survey> findAll(boolean sortByActive) {
//        checkActive();
        if (sortByActive) {
            return surveyRepo.findAll(Sort.by("active"));
        } else {
            return surveyRepo.findAll();
        }
    }

    public List<Survey> findWithSortByName(boolean sortByActive) {
//        checkActive();
        if (sortByActive) {
            return surveyRepo.findAll(Sort.by("active"));
        } else {
            return surveyRepo.findAll(Sort.by("name", "active"));
        }
    }

    public List<Survey> findWithSortByDate(boolean sortByActive) {
//        checkActive();
        if (sortByActive) {
            return surveyRepo.findAll(Sort.by("active"));
        } else {
            return surveyRepo.findAll(Sort.by("startDate", "active"));
        }
    }

    public Optional<Survey> findByName(String name) {
        return surveyRepo.findByName(name);
    }

    @Transactional
    public void save(Survey survey) {
        enrichSurvey(survey);
        surveyRepo.save(survey);
    }

    @Transactional
    public void delete(int id) {
        surveyRepo.deleteById(id);
    }

    @Transactional
    public void update(int id, Survey updatedSurvey) {
        updatedSurvey.setId(id);
        enrichSurvey(updatedSurvey);
        surveyRepo.save(updatedSurvey);
    }

    private void enrichSurvey(Survey survey) {
        survey.setStartDate(Instant.now());
        survey.setEndDate(Instant.now().plus(1, ChronoUnit.MINUTES));
        survey.setActive(true);
    }

}
