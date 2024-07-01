package com.payoman.campaign.controller;

import com.payoman.campaign.model.BoothAgent;
import com.payoman.campaign.model.Candidate;
import com.payoman.campaign.service.BoothAgentActivityService;
import com.payoman.campaign.service.BoothAgentService;
import com.payoman.campaign.service.CandidateService;
import com.payoman.campaign.util.AwsUtils;
import com.payoman.campaign.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.payoman.campaign.util.URLShortenerService.shortenerUrl;

@RestController
@RequestMapping("/admin-act")
@Slf4j
public class AdminActivityController {

    @Autowired
    private Environment environment;

    @Autowired
    private AwsUtils awsUtils;

    @Autowired
    private BoothAgentService boothAgentService;

    @Autowired
    private CandidateService candidateService;

    @Autowired
    private BoothAgentActivityService boothAgentActivityService;

    @PostMapping("/booth-agent")
    public ResponseEntity<Object> createBoothAgent(@RequestParam(value = "party_id", required = false) String partyId,
                                                   @RequestParam(value = "election_id", required = false) String electionId,
                                                   @RequestBody ArrayList<BoothAgent> boothAgent) {
        List<BoothAgent> result = boothAgentService.create(boothAgent, partyId, electionId);
        if (result != null)
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(result);
        return ResponseEntity.internalServerError().contentType(MediaType.APPLICATION_JSON).body("{\"status\" : \"FAILED\"}");
    }

    @PostMapping("/candidate")
    public ResponseEntity<Object> createCandidate(@RequestBody ArrayList<Candidate> candidates) {
        List<Candidate> result = candidateService.create(candidates);
        if (result != null)
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(result);
        return ResponseEntity.internalServerError().contentType(MediaType.APPLICATION_JSON).body("{\"status\" : \"FAILED\"}");
    }

    @PostMapping("/upload-cd")
    public ResponseEntity<Object> uploadDocuments(@RequestPart(value = "document", required = false) MultipartFile file,
                                                  @RequestPart(value = "doc_type", required = false) String documentType,
                                                  @RequestPart(value = "doc_name", required = false) String documentName,
                                                  @RequestPart(value = "url", required = false) String website,
                                                  @RequestParam("candidate-phone") String phone) {
        if (candidateService.isCandidateAvailable(phone)) {
            log.info("File uploading started...");
            String url = "";
            if (file != null) {
                url = awsUtils.convertFileAndUpload(file, environment.getProperty("s3.bucket.name.e-camp") + phone + "/" + documentType);
                url = shortenerUrl(url);
            } else {
                url = website;
            }
            if (url.equals("") || url == null) {
                log.debug("Failed To Upload File, Please check");
                return new ResponseEntity<>("Failed To Upload File, Please check", HttpStatus.BAD_REQUEST);
            } else {
                Map<String, Object> result = candidateService.updateCandidate(phone, documentType, documentName, url);
                if (result != null && result.get("status").toString().equals(Constants.SUCCESS)) {
                    log.info("Successfully Completed task.");
                    return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body("{\"status\":\"SUCCESS\"}");
                }
                log.error("Unsuccessful while saving data");
            }
        }
        log.error("Candidate Not Found");
        return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body("{\"status\":\"FAILED\"}");
    }
}
