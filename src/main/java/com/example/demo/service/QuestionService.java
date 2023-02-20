package com.example.demo.service;

import com.example.demo.model.Question;
import com.example.demo.repository.QuestionRepo;
import com.example.demo.util.QuestionValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import java.util.List;

import static com.example.demo.util.ErrorsUtil.returnErrorsToClient;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepo questionRepo;
    private final QuestionValidator questionValidator;

    public List<Question> findAll() {
        return questionRepo.findAll();
    }

    @Transactional
    public void delete(long id) {
        questionRepo.deleteById(id);
    }

    @Transactional
    public void save(Question question) {
        questionRepo.save(question);
    }

    @Transactional
    public void update(long id, Question questionToEdit) {
        questionToEdit.setId(id);
        questionRepo.save(questionToEdit);
    }

    public void validate(Question question, BindingResult bindingResult) {
        questionValidator.validate(question, bindingResult);

        if (bindingResult.hasErrors()) {
            returnErrorsToClient(bindingResult);
        }
    }
}
