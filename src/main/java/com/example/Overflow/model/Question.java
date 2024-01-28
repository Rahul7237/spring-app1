package com.example.Overflow.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "question")
public class Question {

    public Question(){

    }
    @Column(name="id")
    private int id;
    @Column(name="userId")
    private  String userId;
    @Column(name="answer_id")
    private  String answerId;
    @Column(name="quest_desc")
    private  String quesDesc;
    @Column(name="first_name")
    private  String firstName;
    @Column(name="last_name")
    private  String lastName;
    @Column(name="ques_title")
    private  String quesTitle;

    @Column(name="createDate")
    private  Date createDate;
    public String getQuesDesc() {
        return quesDesc;
    }

    public void setQuesDesc(String quesDesc) {
        this.quesDesc = quesDesc;
    }

    public String getQuesTitle() {
        return quesTitle;
    }

    public void setQuesTitle(String quesTitle) {
        this.quesTitle = quesTitle;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAnswerId() {
        return answerId;
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId;
    }



    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }





}
