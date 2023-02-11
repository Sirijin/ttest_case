package com.example.demo.dto;

import com.example.demo.models.Question;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SurveyDTO {
    private String surveyName;
    private List<Question> questions;
}
