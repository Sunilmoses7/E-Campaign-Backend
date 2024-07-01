package com.payoman.campaign.service;

import com.payoman.campaign.model.BoothAgent;

import java.util.List;

public interface BoothAgentService {
    List<BoothAgent> create(List<BoothAgent> boothAgent, String partyId, String electionId);

    boolean isEntityAvailable(Long phone, String src);

    boolean isEntityAvailable(Long phone,String partyId, String src);

    BoothAgent getEntityDetails(Long phone);

    void updateDetails(Long phone, String fcm_token);
}
