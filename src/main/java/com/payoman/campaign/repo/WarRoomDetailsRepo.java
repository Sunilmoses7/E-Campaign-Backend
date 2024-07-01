package com.payoman.campaign.repo;

import com.payoman.campaign.model.WarRoomDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface WarRoomDetailsRepo extends JpaRepository<WarRoomDetails, Integer> {
    ArrayList<WarRoomDetails> findByAssemblyConstituencyNumberAndPartyIdAndElectionId(String assemblyConstituencyNumber, String partyId, String electionId);
}
