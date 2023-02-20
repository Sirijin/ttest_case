package com.example.demo.service;

import com.example.demo.dto.SurveyResponse;
import com.example.demo.mapper.SurveyConvertor;
import com.example.demo.model.Survey;
import com.example.demo.repository.SurveyRepo;
import com.example.demo.util.SurveyValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.demo.util.ErrorsUtil.returnErrorsToClient;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SurveyService {
    private final SurveyRepo surveyRepo;
    private final SurveyConvertor surveyConvertor;
    private final SurveyValidator surveyValidator;


    /*
    public Page<Survey> getSurveyPage(@NonNull SurveyPageSettings pageSettings) {
        Sort surveySort = pageSettings.buildSort();
        Pageable surveyPage = PageRequest.of(pageSettings.getPage(), pageSettings.getElementPerPage(), surveySort);

        return surveyRepo.findAll(surveyPage);
    }
     */

    public List<Survey> findAll() {
        return surveyRepo.findAll();
    }

    public List<Survey> findAll(boolean sortByActive) {
        if (sortByActive) {
            return surveyRepo.findAll(Sort.by("active"));
        } else {
            return surveyRepo.findAll();
        }
    }

    public List<Survey> findWithSortByName(boolean sortByActive) {
        if (sortByActive) {
            return surveyRepo.findAll(Sort.by("active"));
        } else {
            return surveyRepo.findAll(Sort.by("name", "active"));
        }
    }

    public List<Survey> findWithSortByDate(boolean sortByActive) {
        if (sortByActive) {
            return surveyRepo.findAll(Sort.by("active"));
        } else {
            return surveyRepo.findAll(Sort.by("startDate", "active"));
        }
    }

    public SurveyResponse findSurveysWithSortParams(boolean sortByDate, boolean sortByName, boolean sortByActive) {
        if (!sortByDate && !sortByName) {
            return new SurveyResponse(this.findAll(sortByActive).stream().map(surveyConvertor::convertToDTO).collect(Collectors.toList()));
        } else if (sortByDate && !sortByName) {
            return new SurveyResponse(this.findWithSortByDate(sortByActive).stream().map(surveyConvertor::convertToDTO).collect(Collectors.toList()));
        } else {
            return new SurveyResponse(this.findWithSortByName(sortByActive).stream().map(surveyConvertor::convertToDTO).collect(Collectors.toList()));
        }
    }

    public Optional<Survey> getSurveyByName(String name) {
        return surveyRepo.findByName(name);
    }

    @Transactional
    public void save(Survey survey) {
        enrichSurvey(survey);
        surveyRepo.save(survey);
    }

    @Transactional
    public void delete(long id) {
        surveyRepo.deleteById(id);
    }

    @Transactional
    public void update(long id, Survey updatedSurvey) {
        updatedSurvey.setId(id);
        enrichSurvey(updatedSurvey);
        surveyRepo.save(updatedSurvey);
    }

    private void enrichSurvey(Survey survey) {
        survey.setStartDate(Instant.now());
        survey.setEndDate(Instant.now().plus(1, ChronoUnit.MINUTES));
        survey.setActive(true);
    }

    public void validate(Survey survey, BindingResult bindingResult) {
        surveyValidator.validate(survey, bindingResult);

        if (bindingResult.hasErrors()) {
            returnErrorsToClient(bindingResult);
        }
    }

}
