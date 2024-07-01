package com.payoman.campaign.repo;

import com.payoman.campaign.model.ElectionParty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElectionPartyRepo extends JpaRepository<ElectionParty, Integer> {
}
