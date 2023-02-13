package com.example.demo.service;

import com.example.demo.dto.QuestionDTO;
import com.example.demo.model.Question;
import com.example.demo.repository.QuestionRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class QuestionService {
    private final QuestionRepo questionRepo;
    private final SurveyService surveyService;
    private final ModelMapper modelMapper;

    @Autowired
    public QuestionService(QuestionRepo questionRepo, SurveyService surveyService, ModelMapper modelMapper) {
        this.questionRepo = questionRepo;
        this.surveyService = surveyService;
        this.modelMapper = modelMapper;
    }

    public List<Question> findAll() {
        return questionRepo.findAll();
    }

    @Transactional
    public void delete(int id) {
        questionRepo.deleteById(id);
    }

    @Transactional
    public void save(Question question) {
//        enrichQuestion(question);
        questionRepo.save(question);
    }

    @Transactional
    public void update(int id, Question questionToEdit) {
        questionToEdit.setId(id);
        enrichQuestion(questionToEdit);
        questionRepo.save(questionToEdit);
    }

    private void enrichQuestion(Question question) {
        question.setSurvey(surveyService.findByName(question.getSurvey().getName()).get());
    }
}
