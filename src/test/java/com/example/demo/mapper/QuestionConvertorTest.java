package com.example.demo.mapper;

import com.example.demo.dto.QuestionDTO;
import com.example.demo.model.Question;
import com.example.demo.model.Survey;
import com.example.demo.repository.SurveyRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QuestionConvertorTest {

    @Mock
    private SurveyRepo surveyRepo;
    private QuestionConvertor underTest;

    @BeforeEach
    void setUp() {
        underTest = new QuestionConvertor(surveyRepo);
    }

    @Test
    public void testConvertToQuestion() {
        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setText("What is your name?");
        questionDTO.setSomeOrder(1);
        questionDTO.setSurveyId(1L);

        Survey survey = new Survey();
        survey.setId(1L);

        // Mock surveyRepo
        when(surveyRepo.findById(1L)).thenReturn(Optional.of(survey));

        Question question = underTest.convertToQuestion(questionDTO);
        assertEquals("What is your name?", question.getText());
        assertEquals(1, question.getSomeOrder());
        assertEquals(survey, question.getSurvey());
    }

    @Test
    public void testConvertToQuestionWithId() {
        // Create the QuestionDTO object.
        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setText("What is your favorite color?");
        questionDTO.setSomeOrder(1);
        questionDTO.setSurveyId(1L);

        // Mock the surveyRepo.findById method.
        Survey survey = new Survey();
        survey.setId(1L);
        when(surveyRepo.findById(questionDTO.getSurveyId())).thenReturn(Optional.of(survey));

        // Call the method to be tested.
        Question question = underTest.convertToQuestion(2, questionDTO);

        // Verify the result.
        assertEquals(2, question.getId());
        assertEquals("What is your favorite color?", question.getText());
        assertEquals(1, question.getSomeOrder());
        assertEquals(1, question.getSurvey().getId());
    }

    @Test
    public void testConvertToQuestionDTO() {
        // Create the Question object.
        Question question = new Question();
        question.setText("What is your favorite color?");
        question.setSomeOrder(1);

        // Create the Survey object.
        Survey survey = new Survey();
        survey.setId(1L);

        // Set the Survey object as the Question object's survey.
        question.setSurvey(survey);

        // Call the method to be tested.
        QuestionDTO questionDTO = underTest.convertToQuestionDTO(question);

        // Verify the result.
        assertEquals("What is your favorite color?", questionDTO.getText());
        assertEquals(1, questionDTO.getSomeOrder());
        assertEquals(1, questionDTO.getSurveyId());
    }
}