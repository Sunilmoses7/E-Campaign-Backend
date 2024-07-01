package com.payoman.campaign.service;

import com.payoman.campaign.model.TodoList;
import com.payoman.campaign.model.TodoReport;
import com.payoman.campaign.model.WarRoomDetails;

import java.util.ArrayList;

public interface UtilService {

    ArrayList<WarRoomDetails> getWarRoomDetails(String assemblyConstituencyNumber, String partyId, String electionId);

    ArrayList<TodoList> getTodoList(String assemblyConstituencyNumber, String partyId, String electionId);

    void insertTodoReport(ArrayList<TodoReport> todoReports, String assemblyConstituencyNumber, String partyId, String electionId, String boothAgentPhone);

    ArrayList<TodoReport> getTodoReport(String assemblyConstituencyNumber, String partyId, String electionId, String boothAgentPhone);
}
