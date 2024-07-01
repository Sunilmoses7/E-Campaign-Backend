package com.payoman.campaign.repo;

import com.payoman.campaign.model.BoothAgent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoothAgentRepo extends JpaRepository<BoothAgent, Long> {
    Optional<BoothAgent> findByBoothAgentPhoneNumberAndPartyIdAndIsAdmin(Long phone, String partyId, boolean isAdmin);

    Optional<BoothAgent> findByBoothAgentPhoneNumberAndPartyId(Long phone, String partyId);
}
