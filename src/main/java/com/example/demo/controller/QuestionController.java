package com.example.demo.controller;

import com.example.demo.convertor.QuestionConvertor;
import com.example.demo.dto.QuestionDTO;
import com.example.demo.dto.QuestionResponse;
import com.example.demo.model.Question;
import com.example.demo.repository.SurveyRepo;
import com.example.demo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/question")
public class QuestionController {
    private final QuestionService questionService;
    private final SurveyRepo surveyRepo;
    private final QuestionConvertor questionConvertor;

    @Autowired
    public QuestionController(QuestionService questionService, SurveyRepo surveyRepo, QuestionConvertor questionConvertor) {
        this.questionService = questionService;
        this.surveyRepo = surveyRepo;
        this.questionConvertor = questionConvertor;
    }

    @GetMapping
    public QuestionResponse findAll() {
        return new QuestionResponse(questionService.findAll().stream().map(questionConvertor::convertToDTO).collect(Collectors.toList()));
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> add(@RequestBody QuestionDTO questionDTO) {
        Question questionToAdd = questionConvertor.convertToQuestion(questionDTO);
        questionService.save(questionToAdd);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<HttpStatus> edit(@RequestBody QuestionDTO questionDTO, @PathVariable("id") int id) {
        Question questionToEdit = questionConvertor.convertToQuestion(questionDTO);
        questionService.update(id, questionToEdit);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") int id) {
        questionService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }



}
