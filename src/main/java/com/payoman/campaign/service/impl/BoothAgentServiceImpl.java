package com.payoman.campaign.service.impl;

import com.payoman.campaign.model.BoothAgent;
import com.payoman.campaign.repo.BoothAgentRepo;
import com.payoman.campaign.service.BoothAgentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BoothAgentServiceImpl implements BoothAgentService {

    @Autowired
    private BoothAgentRepo boothAgentRepo;

    @Override
    public List<BoothAgent> create(List<BoothAgent> boothAgent, String partyId, String electionId) {
        log.info("Booth Agent Created ...");
        return boothAgentRepo.saveAllAndFlush(boothAgent.stream().map(bAgent -> {
            bAgent.setCreated_at();
            bAgent.setPartyId(partyId);
            bAgent.setElectionId(electionId);
            return bAgent;
        }).collect(Collectors.toList()));
    }

    @Override
    public boolean isEntityAvailable(Long phone, String src) {
        log.info("Checking availability ...");
        if (src != null && src.equals("portal")) {
            Optional<BoothAgent> optionalBoothAgent = boothAgentRepo.findById(phone);
            if (optionalBoothAgent.isPresent()) {
                return optionalBoothAgent.get().getIsAdmin();
            } else
                return false;
        } else
            return boothAgentRepo.findById(phone).isPresent();
    }

    @Override
    public boolean isEntityAvailable(Long phone, String partyId, String src) {
        log.info("Checking availability ...");
        if (src != null && src.equals("portal")) {
            return boothAgentRepo.findByBoothAgentPhoneNumberAndPartyIdAndIsAdmin(phone, partyId, true).isPresent();
        }
        return boothAgentRepo.findByBoothAgentPhoneNumberAndPartyId(phone, partyId).isPresent();
    }

    @Override
    public BoothAgent getEntityDetails(Long phone) {
        log.info("Fetching booth agent details ...");
        return boothAgentRepo.findById(phone).get();
    }

    @Override
    @Async
    public void updateDetails(Long phone, String fcm_token) {
        log.info("Updating booth agent details ...");
        boothAgentRepo.findById(phone).ifPresent(ba -> {
            ba.setFcmToken(fcm_token);
            boothAgentRepo.saveAndFlush(ba);
        });
    }
}
