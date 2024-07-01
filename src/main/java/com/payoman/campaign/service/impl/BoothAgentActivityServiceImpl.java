package com.payoman.campaign.service.impl;

import com.payoman.campaign.model.BoothAgentActivity;
import com.payoman.campaign.repo.BoothAgentActivityRepo;
import com.payoman.campaign.service.BoothAgentActivityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BoothAgentActivityServiceImpl implements BoothAgentActivityService {

    @Autowired
    private BoothAgentActivityRepo boothAgentActivityRepo;


    @Override
    @Async
    public void createEntity(BoothAgentActivity boothAgentActivity) {
        log.info("Activity Created...");
        boothAgentActivityRepo.saveAndFlush(boothAgentActivity);
    }
}
