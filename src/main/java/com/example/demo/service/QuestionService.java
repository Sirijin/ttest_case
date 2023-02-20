package com.example.demo.service;

import com.example.demo.dto.QuestionDTO;
import com.example.demo.mapper.QuestionConvertor;
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
    private final QuestionConvertor questionConvertor;
    private final SurveyService surveyService;

    public List<QuestionDTO> findAll() {
        return questionRepo.findAll().stream().map(questionConvertor::convertToQuestionDTO).collect(Collectors.toList());
    }

    @Transactional
    public void delete(long id) {
        questionRepo.deleteById(id);
    }

    @Transactional
    public QuestionDTO save(QuestionDTO questionDTO) {
        Question question = questionConvertor.convertToQuestion(questionDTO);
        question.setSurvey(surveyService.findById(question.getSurvey().getId()));
        return questionConvertor.convertToQuestionDTO(questionRepo.save(question));
    }

    @Transactional
    public Question update(long id, QuestionDTO updatedQuestionDTO) {
        if (questionRepo.existsById(id)) {
            Question question = questionConvertor.convertToQuestion(id, updatedQuestionDTO);
            question.setSurvey(surveyService.findById(updatedQuestionDTO.getSurveyId()));
            return questionRepo.save(question);
        } else {
            throw new RuntimeException("There is no question with this id");
        }
    }
}
