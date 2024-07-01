package com.payoman.campaign.controller;

import com.payoman.campaign.model.*;
import com.payoman.campaign.payload.VoterRequest;
import com.payoman.campaign.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/booth-agent")
public class BoothAgentResource {

    @Autowired
    private BoothAgentService boothAgentService;

    @Autowired
    private VoterService voterService;

    @Autowired
    private BoothAgentActivityService boothAgentActivityService;

    @Autowired
    private CandidateService candidateService;

    @Autowired
    private SurveyInformationService surveyInformationService;

    @Autowired
    private UtilService utilService;

    @GetMapping("/vd")
    public ResponseEntity<Object> getEntities(@RequestParam(value = "part_nos",required = false) String partNos, @RequestAttribute("b-phone") String boothAgentPhone) {
        List<Voter> voterList = voterService.getData(boothAgentPhone);
        if (voterList.size() > 0) {
            Map<String, Object> result = new HashMap<>();
            boothAgentActivityService.createEntity(new BoothAgentActivity(boothAgentPhone, "Downloaded voter list", "VOTER_LIST_DOWNLOAD", new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis())));
            result.put("data", voterList);
            result.put("size", voterList.size());
            result.put("status", "SUCCESS");
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PutMapping("/voter")
    public ResponseEntity<Object> updateEntity(@RequestBody ArrayList<VoterRequest> voterRequests,
                                               @RequestAttribute("b-phone") String boothAgentPhone,
                                               @RequestParam(value = "party_id", required = false) String partyId,
                                               @RequestParam(value = "election_id", required = false) String electionId) {
        voterService.updateData(voterRequests);
        boothAgentActivityService.createEntity(new BoothAgentActivity(boothAgentPhone, "Updated Phone, latitude, longitude of " + voterRequests.size() + " voters", "VOTER_PHONE_NUMBER_UPDATE", new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()), partyId, electionId));
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body("{\"status\" : \"PROCESSING\"}");
    }

    @GetMapping("/candidate")
    public ResponseEntity<Object> getCandidateDetails(@RequestParam("asc_no") String assemblyConstituencyNumber,
                                                      @RequestParam("party_id") Integer partyId,
                                                      @RequestAttribute("b-phone") String boothAgentPhone,
                                                      @RequestParam(value = "election_id", required = false) String electionId) {
        Candidate candidate = candidateService.getCandidateDetails(assemblyConstituencyNumber, partyId);
        if (candidate != null) {
            Map<String, Object> result = new HashMap<>();
            boothAgentActivityService.createEntity(new BoothAgentActivity(boothAgentPhone, "Downloaded candidate details", "CANDIDATE_DOWNLOAD", new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()), partyId.toString(), electionId));
            result.put("data", candidate);
            result.put("status", "SUCCESS");
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/survey")
    public ResponseEntity<Object> getSurveyDetails(@RequestParam("party_id") String partyId,
                                                   @RequestParam("election_id") String electionId,
                                                   @RequestAttribute("b-phone") String boothAgentPhone) {

        ArrayList<SurveyInformation> surveyInformationList = surveyInformationService.getSurveyInformation(partyId, electionId);
        if (surveyInformationList != null && surveyInformationList.size() > 0) {
            Map<String, Object> result = new HashMap<>();
            boothAgentActivityService.createEntity(new BoothAgentActivity(boothAgentPhone, "Downloaded survey details", "SURVEY_DOWNLOAD", new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()), partyId, electionId));
            result.put("data", surveyInformationList);
            result.put("status", "SUCCESS");
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping("/survey")
    public ResponseEntity<Object> insertSurveyInformation(@RequestParam("party_id") String partyId,
                                                          @RequestParam("election_id") String electionId,
                                                          @RequestAttribute("b-phone") String boothAgentPhone,
                                                          @RequestBody ArrayList<SurveyInformation> surveyInformation) {
        surveyInformationService.createSurveyInfo(partyId, electionId, surveyInformation);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body("{\"status\" : \"PROCESSING\"}");
    }

    @PostMapping("/survey_response")
    public ResponseEntity<Object> updateSurveyResponse(@RequestParam("party_id") String partyId,
                                                       @RequestParam("election_id") String electionId,
                                                       @RequestAttribute("b-phone") String boothAgentPhone,
                                                       @RequestBody ArrayList<SurveyResponses> surveyResponses) {
        surveyInformationService.createEntity(partyId, electionId, surveyResponses, boothAgentPhone);
        boothAgentActivityService.createEntity(new BoothAgentActivity(boothAgentPhone, "Survey details collected", "SURVEY_RESPONSE", new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()), partyId, electionId));
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body("{\"status\" : \"PROCESSING\"}");
    }

    @PostMapping("/voter_survey_data")
    public ResponseEntity<Object> updateVoterSurveyData(@RequestParam("party_id") String partyId,
                                                        @RequestParam("election_id") String electionId,
                                                        @RequestAttribute("b-phone") String boothAgentPhone,
                                                        @RequestBody BoothAgentWiseSurveyData boothAgentWiseSurveyData) {
        surveyInformationService.createVoterSurveyData(partyId, electionId, boothAgentWiseSurveyData, boothAgentPhone);
        boothAgentActivityService.createEntity(new BoothAgentActivity(boothAgentPhone, "color,voting status, survey status collected", "BOOTH_AGENT_WISE_SURVEY_DATA", new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()), partyId, electionId));
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body("{\"status\" : \"PROCESSING\"}");
    }


    @GetMapping("/voter_survey_data")
    public ResponseEntity<Object> getVoterSurveyData(@RequestParam("party_id") String partyId,
                                                     @RequestParam("election_id") String electionId,
                                                     @RequestAttribute("b-phone") String boothAgentPhone) {

        ArrayList<BoothAgentWiseSurveyData> boothAgentWiseSurveyData = surveyInformationService.getVoterSurveyData(partyId, electionId, boothAgentPhone);
        if (boothAgentWiseSurveyData != null && boothAgentWiseSurveyData.size() > 0) {
            Map<String, Object> result = new HashMap<>();
            boothAgentActivityService.createEntity(new BoothAgentActivity(boothAgentPhone, "Downloaded color,voting status, survey status", "BOOTH_AGENT_WISE_SURVEY_DATA_DOWNLOAD", new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()), partyId, electionId));
            result.put("data", boothAgentWiseSurveyData);
            result.put("status", "SUCCESS");
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/war-room-details")
    public ResponseEntity<Object> getWarRoomDetails(@RequestParam("asc_no") String assemblyConstituencyNumber,
                                                    @RequestParam("party_id") String partyId,
                                                    @RequestParam("election_id") String electionId,
                                                    @RequestAttribute("b-phone") String boothAgentPhone) {

        ArrayList<WarRoomDetails> warRoomDetails = utilService.getWarRoomDetails(assemblyConstituencyNumber, partyId, electionId);
        if (warRoomDetails != null && warRoomDetails.size() > 0) {
            Map<String, Object> result = new HashMap<>();
            boothAgentActivityService.createEntity(new BoothAgentActivity(boothAgentPhone, "Downloaded War Room Details", "WAR_ROOM_DETAILS", new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()), partyId, electionId));
            result.put("data", warRoomDetails);
            result.put("status", "SUCCESS");
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/todo-list")
    public ResponseEntity<Object> getTodoList(@RequestParam("asc_no") String assemblyConstituencyNumber,
                                              @RequestParam("party_id") String partyId,
                                              @RequestParam("election_id") String electionId,
                                              @RequestAttribute("b-phone") String boothAgentPhone) {

        ArrayList<TodoList> todoLists = utilService.getTodoList(assemblyConstituencyNumber, partyId, electionId);
        if (todoLists != null && todoLists.size() > 0) {
            Map<String, Object> result = new HashMap<>();
            boothAgentActivityService.createEntity(new BoothAgentActivity(boothAgentPhone, "Downloaded War Room Details", "WAR_ROOM_DETAILS", new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()), partyId, electionId));
            result.put("data", todoLists);
            result.put("status", "SUCCESS");
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping("/todo-report")
    public ResponseEntity<Object> insertTodoReport(@RequestBody ArrayList<TodoReport> todoReports,
                                                   @RequestParam("asc_no") String assemblyConstituencyNumber,
                                                   @RequestParam("party_id") String partyId,
                                                   @RequestParam("election_id") String electionId,
                                                   @RequestAttribute("b-phone") String boothAgentPhone) {
        utilService.insertTodoReport(todoReports, assemblyConstituencyNumber, partyId, electionId, boothAgentPhone);
        boothAgentActivityService.createEntity(new BoothAgentActivity(boothAgentPhone, "Todo Reports Collected", "TODO_REPORTS_INSERTION", new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis())));
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body("{\"status\" : \"PROCESSING\"}");
    }

    @GetMapping("/todo-report")
    public ResponseEntity<Object> getTodoReport(@RequestParam("asc_no") String assemblyConstituencyNumber,
                                                @RequestParam("party_id") String partyId,
                                                @RequestParam("election_id") String electionId,
                                                @RequestAttribute("b-phone") String boothAgentPhone) {

        ArrayList<TodoReport> todoReports = utilService.getTodoReport(assemblyConstituencyNumber, partyId, electionId, boothAgentPhone);
        if (todoReports != null && todoReports.size() > 0) {
            Map<String, Object> result = new HashMap<>();
            boothAgentActivityService.createEntity(new BoothAgentActivity(boothAgentPhone, "Downloaded War Room Details", "WAR_ROOM_DETAILS", new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()), partyId, electionId));
            result.put("data", todoReports);
            result.put("status", "SUCCESS");
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
