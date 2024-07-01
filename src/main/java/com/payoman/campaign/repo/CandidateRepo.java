package com.payoman.campaign.repo;

import com.payoman.campaign.model.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidateRepo extends JpaRepository<Candidate, Integer> {
    Candidate findByAssemblyConstituencyNumberAndPartyId(String assemblyConstituencyNumber, Integer partyId);

    Candidate findByCandidatePhoneNumber(String candidatePhone);
}
