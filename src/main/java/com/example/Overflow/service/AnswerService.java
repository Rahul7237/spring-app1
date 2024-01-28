package com.example.Overflow.service;

import com.example.Overflow.model.Answer;
import com.example.Overflow.model.Question;
import com.example.Overflow.repository.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class AnswerService {
    @Autowired
    private AnswerRepository answerRepository;

    public List<Answer> allAnswers(){
        return answerRepository.findAll();
    }
    public Answer getById(int id){
        return  answerRepository.findById(id).get();
    }

    public void saveAns(Answer answer){

        answerRepository.save(answer);

    }
    public void Deleted(int id){
        answerRepository.deleteById(id);

    }


}
