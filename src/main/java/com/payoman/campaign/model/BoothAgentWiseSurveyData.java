package com.payoman.campaign.model;

import com.payoman.campaign.model.idClass.BoothAgentWiseSurveyDataId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Data
@Entity
@IdClass(BoothAgentWiseSurveyDataId.class)
public class BoothAgentWiseSurveyData {

    @Id
    private String voterId;

    @Id
    private String electionId;

    @Id
    private String partyId;

    private String boothAgentPhone;

    private String colorCode;

    private Boolean isVoted;

    private Boolean isSurveyCompleted;
}
