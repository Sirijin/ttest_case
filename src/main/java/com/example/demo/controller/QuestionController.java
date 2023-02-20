package com.example.demo.controller;

import com.example.demo.dto.QuestionDTO;
import com.example.demo.dto.QuestionResponse;
import com.example.demo.mapper.QuestionConvertor;
import com.example.demo.model.Question;
import com.example.demo.service.QuestionService;
import com.example.demo.util.QuestionValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/question")
@RequiredArgsConstructor
@Slf4j
public class QuestionController {
    private final QuestionService questionService;
    private final QuestionConvertor questionConvertor;
    private final QuestionValidator questionValidator;


    @GetMapping
    public QuestionResponse getQuestions() {
        return new QuestionResponse(questionService.findAll().stream().map(questionConvertor::convertToDTO).collect(Collectors.toList()));
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addNewQuestion(@RequestBody @Valid QuestionDTO questionDTO, BindingResult bindingResult) {
        Question questionToAdd = questionConvertor.convertToQuestion(questionDTO);

        questionService.validate(questionToAdd, bindingResult);

        questionService.save(questionToAdd);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<HttpStatus> editExistingQuestion(@RequestBody @Valid QuestionDTO questionDTO,
                                                           @PathVariable("id") int id, BindingResult bindingResult) {
        Question questionToEdit = questionConvertor.convertToQuestion(questionDTO);

        questionService.validate(questionToEdit, bindingResult);

        questionService.update(id, questionToEdit);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteQuestion(@PathVariable("id") int id) {
        questionService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}