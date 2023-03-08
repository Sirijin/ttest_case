package com.example.demo.service;

import com.example.demo.dto.QuestionDTO;
import com.example.demo.mapper.QuestionConverter;
import com.example.demo.model.Question;
import com.example.demo.repository.QuestionRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepo questionRepo;
    private final QuestionConverter questionConverter;
    private final SurveyService surveyService;

    public List<QuestionDTO> findAll() {
        return questionRepo.findAll().stream().map(questionConverter::convertToQuestionDTO).collect(Collectors.toList());
    }

    @Transactional
    public void delete(long id) {
        questionRepo.deleteById(id);
    }

    @Transactional
    public QuestionDTO save(QuestionDTO questionDTO) {
        Question question = questionConverter.convertToQuestion(questionDTO);
        question.setSurvey(surveyService.findById(question.getSurvey().getId()));
        return questionConverter.convertToQuestionDTO(questionRepo.save(question));
    }

    @Transactional
    public QuestionDTO update(long id, QuestionDTO updatedQuestionDTO) {
        Question question = questionRepo.findById(id).orElseThrow(() -> new RuntimeException("There is no question with this id"));
        question.setText(updatedQuestionDTO.getText());
        question.setSomeOrder(updatedQuestionDTO.getSomeOrder());
        question.setSurvey(surveyService.findById(updatedQuestionDTO.getSurveyId()));
        return questionConverter.convertToQuestionDTO(question);
    }


}
