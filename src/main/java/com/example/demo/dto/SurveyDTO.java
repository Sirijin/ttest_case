package com.example.demo.dto;

import com.example.demo.model.Question;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SurveyDTO {
    @NotNull
    private String name;
    private List<QuestionDTO> questions;
}
