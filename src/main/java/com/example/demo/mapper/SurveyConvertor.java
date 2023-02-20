package com.example.demo.mapper;

import com.example.demo.dto.QuestionDTO;
import com.example.demo.dto.SurveyDTO;
import com.example.demo.model.Question;
import com.example.demo.model.Survey;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SurveyConvertor {
    private final QuestionConvertor questionConvertor;

    public SurveyDTO convertToDTO(Survey survey) {
        SurveyDTO surveyDTO = new SurveyDTO();
        List<QuestionDTO> questionDTOS = new ArrayList<>();

        surveyDTO.setName(survey.getName());

        for (Question question : survey.getQuestions()) {
            questionDTOS.add(questionConvertor.convertToDTO(question));
        }
        surveyDTO.setQuestions(questionDTOS);
        return surveyDTO;
    }

    public Survey convertToSurvey(SurveyDTO surveyDTO) {
        Survey survey = new Survey();
        List<Question> questions = new ArrayList<>();

        survey.setName(surveyDTO.getName());

        for (QuestionDTO questionDTO : surveyDTO.getQuestions()) {
            questions.add(questionConvertor.convertToQuestion(questionDTO));
        }
        survey.setQuestions(questions);
        return survey;
    }
}
