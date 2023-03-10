package com.example.demo.mapper;

import com.example.demo.dto.QuestionDTO;
import com.example.demo.model.Question;
import com.example.demo.repository.SurveyRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QuestionConverter {
    private final SurveyRepo surveyRepo;

    public Question convertToQuestion(QuestionDTO questionDTO) {
        Question question = new Question();
        extracted(questionDTO, question);

        return question;
    }

    public QuestionDTO convertToQuestionDTO(Question question) {
        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setText(question.getText());
        questionDTO.setSomeOrder(question.getSomeOrder());
        questionDTO.setSurveyId(question.getSurvey().getId());

        return questionDTO;
    }

    public Question convertToQuestion(long id, QuestionDTO questionDTO) {
        Question question = new Question();
        question.setId(id);
        extracted(questionDTO, question);

        return question;
    }

    private void extracted(QuestionDTO questionDTO, Question question) {
        question.setText(questionDTO.getText());
        question.setSomeOrder(questionDTO.getSomeOrder());
        question.setSurvey(surveyRepo.findById(questionDTO.getSurveyId()).orElseThrow(() -> new RuntimeException("not found")));
    }
}
