package com.example.demo.convertor;

import com.example.demo.dto.QuestionDTO;
import com.example.demo.model.Question;
import com.example.demo.repository.SurveyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QuestionConvertor {
    private final SurveyRepo surveyRepo;

    @Autowired
    public QuestionConvertor(SurveyRepo surveyRepo) {
        this.surveyRepo = surveyRepo;
    }

    public Question convertToQuestion(QuestionDTO questionDTO) {
        Question question = new Question();
        question.setText(questionDTO.getText());
        question.setSome_order(questionDTO.getSome_order());
        question.setSurvey(surveyRepo.findById(questionDTO.getSurvey_id()).orElseThrow(() -> new RuntimeException("not found")));

        return question;
    }

    public QuestionDTO convertToDTO(Question question) {
        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setText(question.getText());
        questionDTO.setSome_order(question.getSome_order());
        questionDTO.setSurvey_id(question.getSurvey().getId());

        return questionDTO;
    }
}
