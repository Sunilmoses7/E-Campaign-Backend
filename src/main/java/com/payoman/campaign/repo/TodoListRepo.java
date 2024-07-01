package com.payoman.campaign.repo;

import com.payoman.campaign.model.TodoList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface TodoListRepo extends JpaRepository<TodoList, Integer> {
    ArrayList<TodoList> findByAssemblyConstituencyNumberAndPartyIdAndElectionId(String assemblyConstituencyNumber, String partyId, String electionId);
}
