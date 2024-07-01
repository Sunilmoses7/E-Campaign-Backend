package com.payoman.campaign.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Data
@Entity
public class SurveyResponses {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "increment", strategy = "increment")
    private Integer id;

    private String voterId;

    private Integer questionId;

    private String answerSelected;

    @Type(type = "timestamp")
    private Timestamp createdAt;

    private String partyId;

    private String electionId;

    private String boothAgentPhoneNumber;

    @Transient
    private String question;

    public void setCreatedAt() {
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

    public SurveyResponses(Integer id, String voterId, Integer questionId, String answerSelected, Date createdAt, String partyId, String electionId, String boothAgentPhoneNumber, String question) {
        this.id = id;
        this.voterId = voterId;
        this.questionId = questionId;
        this.answerSelected = answerSelected;
        this.createdAt = (Timestamp) createdAt;
        this.partyId = partyId;
        this.electionId = electionId;
        this.boothAgentPhoneNumber = boothAgentPhoneNumber;
        this.question = question;
    }

    public SurveyResponses() {
    }
}