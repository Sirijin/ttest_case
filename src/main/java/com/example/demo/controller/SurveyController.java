package com.example.demo.controller;

import com.example.demo.convertor.SurveyConvertor;
import com.example.demo.dto.SurveyDTO;
import com.example.demo.dto.SurveyResponse;
import com.example.demo.model.Survey;
import com.example.demo.service.QuestionService;
import com.example.demo.service.SurveyService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/survey")
@RequiredArgsConstructor
public class SurveyController {
    private final SurveyService surveyService;
    private final QuestionService questionService;
    private final ModelMapper modelMapper;
    private final SurveyConvertor surveyConvertor;


    @GetMapping
    public SurveyResponse findAll(@RequestParam(value = "sortByDate", required = false) boolean sortByDate,
                                  @RequestParam(value = "sortByName", required = false) boolean sortByName,
                                  @RequestParam(value = "sortByActive", required = false) boolean sortByActive) {
        if (!sortByDate && !sortByName) {
            return new SurveyResponse(surveyService.findAll(sortByActive).stream().map(surveyConvertor::convertToDTO).collect(Collectors.toList()));
        } else if (sortByDate && !sortByName) {
            return new SurveyResponse(surveyService.findWithSortByDate(sortByActive).stream().map(surveyConvertor::convertToDTO).collect(Collectors.toList()));
        } else if (!sortByDate) {
            return new SurveyResponse(surveyService.findWithSortByName(sortByActive).stream().map(surveyConvertor::convertToDTO).collect(Collectors.toList()));
        } else {
            return null;
        }
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> add(@RequestBody SurveyDTO surveyDTO) {
        Survey surveyToAdd = surveyConvertor.convertToSurvey(surveyDTO);
        surveyService.save(surveyToAdd);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<HttpStatus> edit(@RequestBody SurveyDTO surveyDTO, @PathVariable("id") int id) {
        Survey surveyToEdit = surveyConvertor.convertToSurvey(surveyDTO);
        surveyService.update(id, surveyToEdit);
        return ResponseEntity.ok(HttpStatus.OK);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> delete(@RequestBody Survey survey, @PathVariable("id") int id) {
        surveyService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }



}
