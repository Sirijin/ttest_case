package com.example.demo.util;

import com.example.demo.model.Survey;
import com.example.demo.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Objects;

@Component
public class SurveyValidator implements Validator {
    private final SurveyService surveyService;

    @Autowired
    public SurveyValidator(@Lazy SurveyService surveyService) {
        this.surveyService = surveyService;
    }


    @Override
    public boolean supports(Class<?> clazz) {
        return Survey.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Survey survey = (Survey) target;

        if (surveyService.findAll().stream().anyMatch(survey1 -> Objects.equals(survey1.getName(), survey.getName()))) {
            errors.rejectValue("name", "There is a survey with this name already");
        }
    }
}
