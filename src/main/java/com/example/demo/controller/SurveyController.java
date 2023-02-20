package com.example.demo.controller;

import com.example.demo.dto.SurveyDTO;
import com.example.demo.dto.SurveyResponse;
import com.example.demo.mapper.SurveyConvertor;
import com.example.demo.model.Survey;
import com.example.demo.service.SurveyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/survey")
@RequiredArgsConstructor
@Slf4j
public class SurveyController {
    private final SurveyService surveyService;
    private final SurveyConvertor surveyConvertor;


    @GetMapping
    public SurveyResponse findAll(@RequestParam(value = "sortByDate", required = false) boolean sortByDate,
                                  @RequestParam(value = "sortByName", required = false) boolean sortByName,
                                  @RequestParam(value = "sortByActive", required = false) boolean sortByActive) {
        return surveyService.findSurveysWithSortParams(sortByDate, sortByName, sortByActive);
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addNewSurvey(@RequestBody @Valid SurveyDTO surveyDTO, BindingResult bindingResult) {
        Survey surveyToAdd = surveyConvertor.convertToSurvey(surveyDTO);

        surveyService.validate(surveyToAdd, bindingResult);

        surveyService.save(surveyToAdd);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<HttpStatus> editExistingSurvey(@RequestBody @Valid SurveyDTO surveyDTO,
                                                         @PathVariable("id") int id, BindingResult bindingResult) {
        Survey surveyToEdit = surveyConvertor.convertToSurvey(surveyDTO);

        surveyService.validate(surveyToEdit, bindingResult);

        surveyService.update(id, surveyToEdit);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteSurvey(@PathVariable("id") int id) {
        surveyService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
