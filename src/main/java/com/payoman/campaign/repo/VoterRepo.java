package com.payoman.campaign.repo;

import com.payoman.campaign.model.Voter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoterRepo extends JpaRepository<Voter, String> {

    List<Voter> findByPartNoInAndACNo(String[] partNos, String assemblyConstituencyNumber);
}
