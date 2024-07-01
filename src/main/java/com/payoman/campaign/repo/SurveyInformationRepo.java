package com.payoman.campaign.repo;

import com.payoman.campaign.model.SurveyInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface SurveyInformationRepo extends JpaRepository<SurveyInformation, Integer> {
    ArrayList<SurveyInformation> findByPartyIdAndElectionId(String partyId, String electionId);
}
