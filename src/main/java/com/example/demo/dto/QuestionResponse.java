package com.example.demo.dto;

import com.example.demo.model.Question;

import java.util.List;

public class QuestionResponse {
    private List<QuestionDTO> questions;

    public QuestionResponse(List<QuestionDTO> questions) {
        this.questions = questions;
    }

    public List<QuestionDTO> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionDTO> questions) {
        this.questions = questions;
    }
}
