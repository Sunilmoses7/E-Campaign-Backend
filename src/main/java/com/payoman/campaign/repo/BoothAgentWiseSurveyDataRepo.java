package com.payoman.campaign.repo;

import com.payoman.campaign.model.BoothAgentWiseSurveyData;
import com.payoman.campaign.model.idClass.BoothAgentWiseSurveyDataId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface BoothAgentWiseSurveyDataRepo extends JpaRepository<BoothAgentWiseSurveyData, BoothAgentWiseSurveyDataId> {
    ArrayList<BoothAgentWiseSurveyData> findByBoothAgentPhoneAndPartyIdAndElectionId(String boothAgentPhone, String partyId, String electionId);
}
