package com.payoman.campaign.service;

import com.payoman.campaign.model.BoothAgentWiseSurveyData;
import com.payoman.campaign.model.SurveyInformation;
import com.payoman.campaign.model.SurveyResponses;

import java.util.ArrayList;

public interface SurveyInformationService {
    ArrayList<SurveyInformation> getSurveyInformation(String partyId, String electionId);

    void createEntity(String partyId, String electionId, ArrayList<SurveyResponses> surveyResponses, String boothAgentPhone);

    void createSurveyInfo(String partyId, String electionId, ArrayList<SurveyInformation> surveyInformation);

    void createVoterSurveyData(String partyId, String electionId, BoothAgentWiseSurveyData boothAgentWiseSurveyData, String boothAgentPhone);

    ArrayList<BoothAgentWiseSurveyData> getVoterSurveyData(String partyId, String electionId, String boothAgentPhone);
}
