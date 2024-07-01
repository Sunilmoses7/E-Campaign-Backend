package com.payoman.campaign.service;

import com.payoman.campaign.model.Voter;
import com.payoman.campaign.payload.VoterRequest;

import java.util.ArrayList;
import java.util.List;

public interface VoterService {

    void create(List<Voter> voterList);

    List<Voter> getData(String boothAgentPhone);

    void updateData(ArrayList<VoterRequest> voterRequests);
}
