package com.example.Overflow.service;

import com.example.Overflow.model.Question;
import com.example.Overflow.model.User;
import com.example.Overflow.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class QuestionService {
    @Autowired
    private QuestionRepository questionRepository;


    public List<Question> allQuestion() {
        Sort sort = Sort.by(Sort.Direction.DESC, "createDate");
        return questionRepository.findAll(sort);
    }
    public Question getById(int id){
        return  questionRepository.findById(id).get();
    }

    public void saveQues(Question question){

        questionRepository.save(question);

    }

    public void Deleted(int id){
        questionRepository.deleteById(id);

    }
}
