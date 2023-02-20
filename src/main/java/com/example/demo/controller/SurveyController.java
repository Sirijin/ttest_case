package com.example.demo.controller;

import com.example.demo.dto.SurveyDTO;
import com.example.demo.service.SurveyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import static com.example.demo.controller.ErrorHandler.bindingHandle;

@RestController
@RequestMapping("/survey")
@RequiredArgsConstructor
public class SurveyController {
    private final SurveyService surveyService;


    @GetMapping("/{id}")
    public ResponseEntity<?> getSurvey(@PathVariable Long id) {

        try {
            return ResponseEntity.ok(surveyService.getSurvey(id));
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping()
    public ResponseEntity<?> getSurveyList(@RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                           @RequestParam(value = "size", defaultValue = "2", required = false) int size,
                                           @RequestParam(value = "sort", defaultValue = "id", required = false) String sort,
                                           @RequestParam(value = "direction", defaultValue = "ASC", required = false) String direction) {
        try {
            return ResponseEntity.ok(surveyService.getSurveyList(PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort))));
        } catch (Exception e) {
            return null;
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addNewSurvey(@RequestBody @Valid SurveyDTO surveyDTO, BindingResult bindingResult) {
        try {
            bindingHandle(bindingResult);
            return ResponseEntity.ok(surveyService.save(surveyDTO));
        } catch (Exception e) {
            return null;
        }
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> editExistingSurvey(@RequestBody @Valid SurveyDTO surveyDTO,
                                                @PathVariable("id") int id, BindingResult bindingResult) {
        try {
            bindingHandle(bindingResult);
            return ResponseEntity.ok(surveyService.update(id, surveyDTO));
        } catch (Exception e) {
            return null;
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteSurvey(@PathVariable("id") int id) {
        try {
            surveyService.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return null;
        }
    }
}
