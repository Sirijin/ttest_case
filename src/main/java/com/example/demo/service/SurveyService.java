package com.example.demo.service;

import com.example.demo.dto.SurveyDTO;
import com.example.demo.mapper.SurveyConvertor;
import com.example.demo.model.Survey;
import com.example.demo.repository.SurveyRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SurveyService {
    private final SurveyRepo surveyRepo;
    private final SurveyConvertor surveyConvertor;

    public SurveyDTO getSurvey(long id) {
        return surveyConvertor.convertToDTO(surveyRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Survey with this id does not exist")));
    }

    public List<SurveyDTO> getSurveyList(Pageable pageable) {
        return surveyRepo.findAll(pageable).stream().map(surveyConvertor::convertToDTO).collect(Collectors.toList());
    }

    @Transactional
    public SurveyDTO save(SurveyDTO surveyDTO) {

        Survey survey = surveyConvertor.convertToSurvey(surveyDTO);
        survey.setStartDate(Instant.now());
        survey.setEndDate(Instant.now().plus(1, ChronoUnit.MINUTES));
        survey.setActive(true);

        return surveyConvertor.convertToDTO(surveyRepo.save(survey));
    }

    @Transactional
    public void delete(long id) {
        surveyRepo.deleteById(id);
    }

    @Transactional
    public Survey update(long id, SurveyDTO updatedSurveyDTO) {
        if (surveyRepo.existsById(id)) {
            Survey survey = surveyConvertor.convertToSurvey(id, updatedSurveyDTO);
            return surveyRepo.save(survey);
        } else {
            throw new RuntimeException("Survey with this id does not exist");
        }
    }

    public Survey findById(Long id) {
        return surveyRepo.findById(id).orElseThrow(RuntimeException::new);
    }
}