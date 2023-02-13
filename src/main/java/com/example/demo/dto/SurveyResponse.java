package com.example.demo.dto;

import java.util.List;

public class SurveyResponse {
    private List<SurveyDTO> surveys;

    public SurveyResponse(List<SurveyDTO> surveys) {
        this.surveys = surveys;
    }

    public List<SurveyDTO> getSurveys() {
        return surveys;
    }

    public void setSurveys(List<SurveyDTO> surveys) {
        this.surveys = surveys;
    }
}
