package com.payoman.campaign.model.idClass;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@EqualsAndHashCode
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoothAgentWiseSurveyDataId implements Serializable {
    private String voterId;
    private String electionId;
    private String partyId;
}
