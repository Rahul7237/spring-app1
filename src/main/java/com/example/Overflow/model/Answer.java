package com.example.Overflow.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "answer")
public class Answer {

    public Answer(){

    }

    @Column(name="id")
    private int id;
    @Column(name="userId")
    private  int userId;
    @Column(name="quesId")

    private  int quesId;
    @Column(name="quest_desc")
    private  String quest_desc;
    @Column(name="first_name")
    private  String firstName;
    @Column(name="last_name")
    private  String lastName;
    @Column(name="answer_desc")
    private  String answer_desc;

    @Column(name="createDate")
    private  Date createDate;

    @Column(name="location")
    private  String location;
    @Column(name="latitude")
    private  String latitude;
    @Column(name="longitude")
    private  String longitude;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public int getQuesId() {
        return quesId;
    }

    public void setQuesId(int quesId) {
        this.quesId = quesId;
    }

    public String getQuest_desc() {
        return quest_desc;
    }

    public void setQuest_desc(String quest_desc) {
        this.quest_desc = quest_desc;
    }

    public String getAnswer_desc() {
        return answer_desc;
    }

    public void setAnswer_desc(String answer_desc) {
        this.answer_desc = answer_desc;
    }




}
