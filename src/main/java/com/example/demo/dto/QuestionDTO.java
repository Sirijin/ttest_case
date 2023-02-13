package com.example.demo.dto;

import com.example.demo.model.Survey;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class QuestionDTO {
    @NotNull
    private int survey_id;
    @NotNull
    private String text;
    @NotNull
    private int some_order;
}
