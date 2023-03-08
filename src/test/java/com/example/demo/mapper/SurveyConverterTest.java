package com.example.demo.mapper;

import com.example.demo.dto.QuestionDTO;
import com.example.demo.dto.SurveyDTO;
import com.example.demo.model.Question;
import com.example.demo.model.Survey;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class SurveyConverterTest {

    @Mock
    private QuestionConverter questionConverter;
    private SurveyConverter underTest;

    @BeforeEach
    void setUp() {
        underTest = new SurveyConverter(questionConverter);
    }

    @Test
    public void testConvertToDTO() {
        // Create a Survey object and a list of questions
        Survey survey = new Survey();
        survey.setName("My Survey");
        List<Question> questions = new ArrayList<>();

        // Add some questions to the list
        questions.add(new Question());
        questions.add(new Question());

        // Set the questions in the Survey
        survey.setQuestions(questions);

        // Call the convertToDTO() method
        SurveyDTO surveyDTO = underTest.convertToDTO(survey);

        // Assert that the SurveyDTO has the same name as the Survey
        assertEquals(survey.getName(), surveyDTO.getName());

        // Assert that the SurveyDTO has the same number of questions as the Survey
        assertEquals(survey.getQuestions().size(), surveyDTO.getQuestions().size());
    }

    @Test
    public void testConvertToSurvey() {
        SurveyDTO surveyDTO = new SurveyDTO();
        surveyDTO.setName("Survey name");

        List<QuestionDTO> questionsDTO = new ArrayList<>();
        QuestionDTO questionDTO1 = new QuestionDTO();
        questionDTO1.setText("Question 1");
        questionsDTO.add(questionDTO1);
        QuestionDTO questionDTO2 = new QuestionDTO();
        questionDTO2.setText("Question 2");
        questionsDTO.add(questionDTO2);
        surveyDTO.setQuestions(questionsDTO);

        Survey survey = underTest.convertToSurvey(surveyDTO);

        assertNotNull(survey);
        assertEquals(survey.getName(), surveyDTO.getName());
        assertEquals(survey.getQuestions().size(), surveyDTO.getQuestions().size());
    }

    @Test
    public void testConvertToSurveyWithId() {
        long id = 1;
        SurveyDTO surveyDTO = new SurveyDTO();
        surveyDTO.setName("Survey name");

        List<QuestionDTO> questionsDTO = new ArrayList<>();
        QuestionDTO questionDTO1 = new QuestionDTO();
        questionDTO1.setText("Question 1");
        questionsDTO.add(questionDTO1);
        QuestionDTO questionDTO2 = new QuestionDTO();
        questionDTO2.setText("Question 2");
        questionsDTO.add(questionDTO2);
        surveyDTO.setQuestions(questionsDTO);

        Survey survey = underTest.convertToSurvey(id, surveyDTO);

        assertNotNull(survey);
        assertEquals(survey.getId(), id);
        assertEquals(survey.getName(), surveyDTO.getName());
        assertEquals(survey.getQuestions().size(), surveyDTO.getQuestions().size());
    }
}