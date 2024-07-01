package com.payoman.campaign.controller;


import com.payoman.campaign.model.BoothAgentActivity;
import com.payoman.campaign.model.OldElectionResults;
import com.payoman.campaign.model.SurveyResponses;
import com.payoman.campaign.service.BoothAgentActivityService;
import com.payoman.campaign.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/reports")
public class ReportsController {

    @Autowired
    private ReportService reportService;

    @Autowired
    private BoothAgentActivityService boothAgentActivityService;

    @GetMapping("/survey")
    public ResponseEntity<Object> getSurveyDetails(@RequestParam("party_id") String partyId,
                                                   @RequestParam("election_id") String electionId,
                                                   @RequestParam(value = "ba_phone", required = false) String boothAgentPhoneRequested,
                                                   @RequestAttribute("b-phone") String boothAgentPhone) {
        Map<String, Object> result = new HashMap<>();
        ArrayList<SurveyResponses> surveyInformationList = reportService.getSurveyInformation(partyId, electionId, boothAgentPhoneRequested, boothAgentPhone);
        if (surveyInformationList != null && surveyInformationList.size() > 0) {
            boothAgentActivityService.createEntity(new BoothAgentActivity(boothAgentPhone, "Downloaded survey reports", "SURVEY_REPORT_DOWNLOAD", new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()), partyId, electionId));
            result.put("data", surveyInformationList);
            result.put("status", "SUCCESS");
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/phone_update")
    public ResponseEntity<Object> getPhoneNumberUpdateDetails(@RequestParam("party_id") String partyId,
                                                              @RequestParam("election_id") String electionId,
                                                              @RequestParam(value = "ba_phone", required = false) String boothAgentPhoneRequested,
                                                              @RequestAttribute("b-phone") String boothAgentPhone) {
        Map<String, Object> result = new HashMap<>();
        ArrayList<BoothAgentActivity> boothAgentActivities = reportService.getPhoneNumberUpdateDetails(partyId, electionId, boothAgentPhoneRequested, boothAgentPhone);
        if (boothAgentActivities != null && boothAgentActivities.size() > 0) {
            boothAgentActivityService.createEntity(new BoothAgentActivity(boothAgentPhone, "Downloaded phone number update reports", "PHONE_UPDATE_REPORT_DOWNLOAD", new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()), partyId, electionId));
            result.put("data", boothAgentActivities);
            result.put("status", "SUCCESS");
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/old_election")
    public ResponseEntity<Object> getOldElectionResultReport(@RequestParam("party_id") String partyId,
                                                             @RequestParam("election_id") String electionId,
                                                             @RequestAttribute("b-phone") String boothAgentPhone) {
        Map<String, Object> result = new HashMap<>();
        ArrayList<OldElectionResults> oldElectionResults = reportService.getOldElectionReport(boothAgentPhone,partyId,electionId);
        if (oldElectionResults != null && oldElectionResults.size() > 0) {
            boothAgentActivityService.createEntity(new BoothAgentActivity(boothAgentPhone, "Downloaded old Election reports", "OLD_ELECTION_REPORT_DOWNLOAD", new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis())));
            result.put("data", oldElectionResults);
            result.put("status", "SUCCESS");
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
