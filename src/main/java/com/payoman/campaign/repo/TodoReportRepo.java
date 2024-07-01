package com.payoman.campaign.repo;

import com.payoman.campaign.model.TodoReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface TodoReportRepo extends JpaRepository<TodoReport, Integer> {
    ArrayList<TodoReport> findByAssemblyConstituencyNumberAndPartyIdAndElectionIdAndBoothAgentNumber(String assemblyConstituencyNumber, String partyId, String electionId, String boothAgentPhone);
}
