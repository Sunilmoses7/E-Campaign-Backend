package com.payoman.campaign.repo;

import com.payoman.campaign.model.BoothAgentActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface BoothAgentActivityRepo extends JpaRepository<BoothAgentActivity, Integer> {

    ArrayList<BoothAgentActivity> findByTaskTypeAndPartyIdAndElectionIdAndBoothAgentPhone(String taskType, String partyId, String electionId, String boothAgentPhoneRequested);

    ArrayList<BoothAgentActivity> findByTaskTypeAndPartyIdAndElectionId(String taskType, String partyId, String electionId);
}
