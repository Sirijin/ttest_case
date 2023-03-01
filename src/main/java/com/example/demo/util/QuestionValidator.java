package com.example.demo.util;

import com.example.demo.model.Question;
import com.example.demo.service.SurveyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class QuestionValidator implements Validator {

    private final SurveyService surveyService;

    @Override
    public boolean supports(Class<?> clazz) {
        return Question.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Question question = (Question) target;

        if (question.getSurvey() == null) {
            return;
        }

        if (surveyService.getSurveyByName(question.getSurvey().getName()).isEmpty()) {
            errors.rejectValue("survey", "There is no survey with this name");
        }

        surveyService.getSurveyByName(question.getSurvey().getName()).get().getQuestions()
                .stream()
                .anyMatch(q -> {
                    if (q.getText().equals(question.getText())) {
                        errors.rejectValue("text", "There is already a question with the same text");
                        return true;
                    }

                    if (q.getSomeOrder().equals(question.getSomeOrder())) {
                        errors.rejectValue("someOrder", "There is already a question on this position");
                        return true;
                    } else {
                        return false;
                    }
                });
    }
}

