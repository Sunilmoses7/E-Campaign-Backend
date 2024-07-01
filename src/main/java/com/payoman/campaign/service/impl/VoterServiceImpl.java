package com.payoman.campaign.service.impl;

import com.payoman.campaign.model.BoothAgent;
import com.payoman.campaign.model.Voter;
import com.payoman.campaign.payload.VoterRequest;
import com.payoman.campaign.repo.VoterRepo;
import com.payoman.campaign.service.BoothAgentService;
import com.payoman.campaign.service.VoterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class VoterServiceImpl implements VoterService {

    @Autowired
    private VoterRepo repository;

    @Autowired
    private BoothAgentService boothAgentService;

    @Override
    public void create(List<Voter> voterList) {
        int batchSize = 50;
        int totalObjects = voterList.size();
        for (int i = 0; i < totalObjects; i = i + batchSize) {
            if (i + batchSize > totalObjects) {
                log.info("Inserted Data count +" + i);
                List<Voter> voters = voterList.subList(i, totalObjects - 1);
                repository.saveAll(voters);
                break;
            }
            List<Voter> voters = voterList.subList(i, i + batchSize);
            log.info("Inserted Data count +" + i);
            repository.saveAll(voters);
        }
    }


    @Override
    public List<Voter> getData(String boothAgentPhone) {
        log.info("Downloading voter data ...");
        BoothAgent boothAgent = boothAgentService.getEntityDetails(Long.valueOf(boothAgentPhone));
        return repository.findByPartNoInAndACNo(boothAgent.getPartNo().split(","), boothAgent.getAssemblyConstituencyNumber());
    }

    @Override
    @Async
    public void updateData(ArrayList<VoterRequest> voterRequests) {
        log.info("Updating voter details ...");
        voterRequests.stream().forEach(v -> {
            repository.findById(v.getVoterId()).ifPresent(voter -> {
                voter.setMobileNo(v.getPhoneNumber());
                voter.setLatitude(v.getLatitude());
                voter.setLongitude(v.getLongitude());
                repository.saveAndFlush(voter);
            });
        });
    }
}
