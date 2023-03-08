package com.example.demo.service;

import com.example.demo.dto.SurveyDTO;
import com.example.demo.mapper.SurveyConverter;
import com.example.demo.model.Survey;
import com.example.demo.repository.SurveyRepo;
import com.example.demo.util.SurveyNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SurveyService {
    private final SurveyRepo surveyRepo;
    private final SurveyConverter surveyConverter;

    public SurveyDTO getSurvey(long id) {
        return surveyConverter.convertToDTO(surveyRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Survey with this id does not exist")));
    }

    public List<SurveyDTO> getSurveyList(Pageable pageable) {
        return surveyRepo.findAll(pageable).stream().map(surveyConverter::convertToDTO).collect(Collectors.toList());
    }

    @Transactional
    public SurveyDTO save(SurveyDTO surveyDTO) {

        Survey survey = surveyConverter.convertToSurvey(surveyDTO);
        survey.setStartDate(Instant.now());
        survey.setEndDate(Instant.now().plus(1, ChronoUnit.MINUTES));
        survey.setActive(true);

        return surveyConverter.convertToDTO(surveyRepo.save(survey));
    }

    @Transactional
    public void delete(long id) {
        surveyRepo.deleteById(id);
    }

    @Transactional
    public SurveyDTO update(long id, SurveyDTO updatedSurveyDTO) {
        Optional<Survey> optionalSurvey = surveyRepo.findById(id);
        if (optionalSurvey.isPresent()) {
            Survey survey = surveyConverter.convertToSurvey(updatedSurveyDTO);
            survey.setId(id);
            survey.setStartDate(optionalSurvey.get().getStartDate());
            survey.setEndDate(Instant.now().plus(1, ChronoUnit.MINUTES));
            survey.setActive(true);
            return surveyConverter.convertToDTO(surveyRepo.save(survey));
        } else {
            throw new SurveyNotFoundException("Survey with this id does not exist");
        }
    }

    public Survey findById(Long id) {
        return surveyRepo.findById(id).orElseThrow(RuntimeException::new);
    }
}