package com.payoman.campaign.repo;

import com.payoman.campaign.model.OldElectionResults;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface OldElectionResultsRepo extends JpaRepository<OldElectionResults, Integer> {
    ArrayList<OldElectionResults> findByAssemblyConstituencyNumber(String assemblyConstituencyNumber);

    ArrayList<OldElectionResults> findByPartNoIn(String[] partNos);
}
