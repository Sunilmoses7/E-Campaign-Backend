package com.payoman.campaign.service.impl;

import com.payoman.campaign.model.*;
import com.payoman.campaign.model.idClass.BoothAgentWiseSurveyDataId;
import com.payoman.campaign.repo.*;
import com.payoman.campaign.service.BoothAgentService;
import com.payoman.campaign.service.ReportService;
import com.payoman.campaign.service.SurveyInformationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SurveyInformationServiceImpl implements SurveyInformationService, ReportService {


    @Autowired
    private SurveyInformationRepo surveyInformationRepo;

    @Autowired
    private SurveyResponsesRepo surveyResponsesRepo;

    @Autowired
    private BoothAgentService boothAgentService;

    @Autowired
    private BoothAgentActivityRepo boothAgentActivityRepo;

    @Autowired
    private OldElectionResultsRepo oldElectionResultsRepo;

    @Autowired
    private BoothAgentWiseSurveyDataRepo boothAgentWiseSurveyDataRepo;

    @Override
    public ArrayList<SurveyInformation> getSurveyInformation(String partyId, String electionId) {
        log.info("Fetching Survey Information ...");
        return surveyInformationRepo.findByPartyIdAndElectionId(partyId, electionId);
    }

    @Override
    @Async
    public void createEntity(String partyId, String electionId, ArrayList<SurveyResponses> surveyResponses, String boothAgentPhone) {
        log.info("Saving survey response ...");
        surveyResponsesRepo.saveAllAndFlush(surveyResponses.stream().map(responses -> {
            responses.setPartyId(partyId);
            responses.setElectionId(electionId);
            responses.setCreatedAt();
            responses.setBoothAgentPhoneNumber(boothAgentPhone);
            return responses;
        }).collect(Collectors.toList()));
    }

    @Override
    @Async
    public void createSurveyInfo(String partyId, String electionId, ArrayList<SurveyInformation> surveyInformation) {
        log.info("Creating new survey information ...");
        surveyInformationRepo.saveAllAndFlush(surveyInformation.stream().map(surInfo -> {
            surInfo.setPartyId(partyId);
            surInfo.setElectionId(electionId);
            surInfo.setCreated_at();
            return surInfo;
        }).collect(Collectors.toList()));
    }

    @Override
    @Async
    public void createVoterSurveyData(String partyId, String electionId, BoothAgentWiseSurveyData boothAgentWiseSurveyData, String boothAgentPhone) {

        Optional<BoothAgentWiseSurveyData> optionalBoothAgentWiseSurveyData = boothAgentWiseSurveyDataRepo.findById(new BoothAgentWiseSurveyDataId(boothAgentWiseSurveyData.getVoterId(), electionId, partyId));

        if (optionalBoothAgentWiseSurveyData.isPresent()) {
            BoothAgentWiseSurveyData data = optionalBoothAgentWiseSurveyData.get();

            if (boothAgentWiseSurveyData.getIsSurveyCompleted() != null)
                data.setIsSurveyCompleted(boothAgentWiseSurveyData.getIsSurveyCompleted());

            if (boothAgentWiseSurveyData.getIsVoted() != null)
                data.setIsVoted(boothAgentWiseSurveyData.getIsVoted());

            if (boothAgentWiseSurveyData.getColorCode() != null && !boothAgentWiseSurveyData.getColorCode().trim().isEmpty())
                data.setColorCode(boothAgentWiseSurveyData.getColorCode());

            boothAgentWiseSurveyDataRepo.save(data);
        } else {
            boothAgentWiseSurveyData.setPartyId(partyId);
            boothAgentWiseSurveyData.setElectionId(electionId);
            boothAgentWiseSurveyData.setBoothAgentPhone(boothAgentPhone);
            boothAgentWiseSurveyDataRepo.save(boothAgentWiseSurveyData);
        }
    }

    @Override
    public ArrayList<BoothAgentWiseSurveyData> getVoterSurveyData(String partyId, String electionId, String boothAgentPhone) {
        return boothAgentWiseSurveyDataRepo.findByBoothAgentPhoneAndPartyIdAndElectionId(boothAgentPhone, partyId, electionId);
    }

    @Override
    public ArrayList<SurveyResponses> getSurveyInformation(String partyId, String electionId, String boothAgentPhoneRequested, String boothAgentPhone) {
        log.info("Fetching survey information report ....");
        if (boothAgentPhoneRequested != null) {
            return surveyResponsesRepo.findByCustomDetails(partyId, electionId, boothAgentPhoneRequested);
        } else if (boothAgentService.isEntityAvailable(Long.parseLong(boothAgentPhone), partyId, "portal")) {
            log.info("Fetching survey information report of all .... " + boothAgentPhone);
            return surveyResponsesRepo.findAllByCustomDetails(partyId, electionId);
        }
        return null;
    }

    @Override
    public ArrayList<BoothAgentActivity> getPhoneNumberUpdateDetails(String partyId, String electionId, String boothAgentPhoneRequested, String boothAgentPhone) {
        log.info("Fetching phone number update information report ....");
        if (boothAgentPhoneRequested != null) {
            return boothAgentActivityRepo.findByTaskTypeAndPartyIdAndElectionIdAndBoothAgentPhone("VOTER_PHONE_NUMBER_UPDATE", partyId, electionId, boothAgentPhoneRequested);
        } else if (boothAgentService.isEntityAvailable(Long.parseLong(boothAgentPhone), partyId, "portal")) {
            log.info("Fetching phone number update information report of all by admin .... " + boothAgentPhone);
            return boothAgentActivityRepo.findByTaskTypeAndPartyIdAndElectionId("VOTER_PHONE_NUMBER_UPDATE", partyId, electionId);
        }
        return null;
    }

    @Override
    public void insertOldReports(List<OldElectionResults> oldElectionResults) {
        int batchSize = 50;
        int totalObjects = oldElectionResults.size();
        for (int i = 0; i < totalObjects; i = i + batchSize) {
            if (i + batchSize > totalObjects) {
                log.info("Inserted Data count +" + i);
                List<OldElectionResults> electionResults = oldElectionResults.subList(i, totalObjects - 1);
                oldElectionResultsRepo.saveAll(electionResults);
                break;
            }
            List<OldElectionResults> electionResults = oldElectionResults.subList(i, i + batchSize);
            log.info("Inserted Data count +" + i);
            oldElectionResultsRepo.saveAll(electionResults);
        }
    }

    @Override
    public ArrayList<OldElectionResults> getOldElectionReport(String boothAgentPhone, String partyId, String electionId) {
        BoothAgent boothAgent = boothAgentService.getEntityDetails(Long.parseLong(boothAgentPhone));
        if (boothAgent != null) {
            if (boothAgent.getIsAdmin() != null && boothAgent.getIsAdmin()) {
                log.info("Fetching old election result information report of all by admin .... " + boothAgentPhone);
                return oldElectionResultsRepo.findByAssemblyConstituencyNumber(boothAgent.getAssemblyConstituencyNumber());
            } else {
                log.info("Fetching old election result information report of one part number .... " + boothAgentPhone);
                return oldElectionResultsRepo.findByPartNoIn(boothAgent.getPartNo().split(","));
            }
        }
        return null;
    }
}
