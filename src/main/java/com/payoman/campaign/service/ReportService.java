package com.payoman.campaign.service;

import com.payoman.campaign.model.BoothAgentActivity;
import com.payoman.campaign.model.OldElectionResults;
import com.payoman.campaign.model.SurveyResponses;

import java.util.ArrayList;
import java.util.List;

public interface ReportService {
    ArrayList<SurveyResponses> getSurveyInformation(String partyId, String electionId, String boothAgentPhoneRequested, String boothAgentPhone);

    ArrayList<BoothAgentActivity> getPhoneNumberUpdateDetails(String partyId, String electionId, String boothAgentPhoneRequested, String boothAgentPhone);

    void insertOldReports(List<OldElectionResults> oldElectionResults);

    ArrayList<OldElectionResults> getOldElectionReport(String boothAgentPhone, String partyId, String electionId);
}
