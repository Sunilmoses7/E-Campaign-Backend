package com.payoman.campaign.service;

import com.payoman.campaign.model.Candidate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface CandidateService {
    List<Candidate> create(ArrayList<Candidate> candidates);

    Candidate getCandidateDetails(String assemblyConstituencyNumber, Integer partyId);

    Map<String, Object> updateCandidate(String candidatePhone, String documentType, String documentName, String url);

    boolean isCandidateAvailable(String phone);
}
