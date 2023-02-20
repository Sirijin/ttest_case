package com.example.demo.controller;

import com.example.demo.dto.QuestionDTO;
import com.example.demo.mapper.QuestionConvertor;
import com.example.demo.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import static com.example.demo.controller.ErrorHandler.bindingHandle;


@RestController
@RequestMapping("/question")
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;
    private final QuestionConvertor questionConvertor;


    @GetMapping
    public ResponseEntity<?> getQuestions() {
        try {
            return ResponseEntity.ok(questionService.findAll());
        } catch (Exception e) {
            return null;
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addNewQuestion(@RequestBody @Valid QuestionDTO questionDTO, BindingResult bindingResult) {
        try {
            bindingHandle(bindingResult);
            return ResponseEntity.ok(questionService.save(questionDTO));
        } catch (Exception e) {
            return null;
        }
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> editExistingQuestion(@RequestBody @Valid QuestionDTO questionDTO,
                                                  @PathVariable("id") int id, BindingResult bindingResult) {
        try {
            bindingHandle(bindingResult);
            return ResponseEntity.ok(questionService.update(id, questionDTO));
        } catch (Exception e) {
            return null;
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteQuestion(@PathVariable("id") int id) {
        try {
            questionService.delete(id);
            return ResponseEntity.ok(HttpStatus.OK);
        } catch (Exception e) {
            return null;
        }
    }
}