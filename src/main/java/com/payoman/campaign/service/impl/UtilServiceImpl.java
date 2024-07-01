package com.payoman.campaign.service.impl;

import com.payoman.campaign.model.TodoList;
import com.payoman.campaign.model.TodoReport;
import com.payoman.campaign.model.WarRoomDetails;
import com.payoman.campaign.repo.TodoListRepo;
import com.payoman.campaign.repo.TodoReportRepo;
import com.payoman.campaign.repo.WarRoomDetailsRepo;
import com.payoman.campaign.service.UtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class UtilServiceImpl implements UtilService {

    @Autowired
    private TodoListRepo todoListRepo;

    @Autowired
    private TodoReportRepo todoReportRepo;

    @Autowired
    private WarRoomDetailsRepo warRoomDetailsRepo;

    @Override
    public ArrayList<WarRoomDetails> getWarRoomDetails(String assemblyConstituencyNumber, String partyId, String electionId) {
        return warRoomDetailsRepo.findByAssemblyConstituencyNumberAndPartyIdAndElectionId(assemblyConstituencyNumber, partyId, electionId);
    }

    @Override
    public ArrayList<TodoList> getTodoList(String assemblyConstituencyNumber, String partyId, String electionId) {
        return todoListRepo.findByAssemblyConstituencyNumberAndPartyIdAndElectionId(assemblyConstituencyNumber, partyId, electionId);
    }

    @Override
    @Async
    public void insertTodoReport(ArrayList<TodoReport> todoReports, String assemblyConstituencyNumber, String partyId, String electionId, String boothAgentPhone) {
        todoReportRepo.saveAllAndFlush(todoReports.stream().map(todoReport -> {
            todoReport.setCreated_at(new Timestamp(System.currentTimeMillis()));
            todoReport.setAssemblyConstituencyNumber(assemblyConstituencyNumber);
            todoReport.setPartyId(partyId);
            todoReport.setBoothAgentNumber(boothAgentPhone);
            todoReport.setElectionId(electionId);
            return todoReport;
        }).collect(Collectors.toList()));
    }

    @Override
    public ArrayList<TodoReport> getTodoReport(String assemblyConstituencyNumber, String partyId, String electionId, String boothAgentPhone) {
        return todoReportRepo.findByAssemblyConstituencyNumberAndPartyIdAndElectionIdAndBoothAgentNumber(assemblyConstituencyNumber, partyId, electionId, boothAgentPhone);
    }
}
