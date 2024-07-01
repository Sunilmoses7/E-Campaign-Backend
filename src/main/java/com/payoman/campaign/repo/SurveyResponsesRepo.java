package com.payoman.campaign.repo;

import com.payoman.campaign.model.SurveyResponses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface SurveyResponsesRepo extends JpaRepository<SurveyResponses, Integer> {


    @Query(value = "SELECT new com.payoman.campaign.model.SurveyResponses(sr.id, sr.voterId, sr.questionId, sr.answerSelected, sr.createdAt, sr.partyId, sr.electionId, sr.boothAgentPhoneNumber, si.question) " +
            "FROM SurveyResponses sr " +
            "LEFT JOIN SurveyInformation si " +
            "ON sr.questionId = si.id " +
            "WHERE sr.boothAgentPhoneNumber = :boothAgentPhoneRequested " +
            "AND sr.partyId = :partyId " +
            "AND sr.electionId = :electionId")
    ArrayList<SurveyResponses> findByCustomDetails(String partyId, String electionId, String boothAgentPhoneRequested);

    @Query(value = "SELECT new com.payoman.campaign.model.SurveyResponses(sr.id, sr.voterId, sr.questionId, sr.answerSelected, sr.createdAt, sr.partyId, sr.electionId, sr.boothAgentPhoneNumber, si.question) " +
            "FROM SurveyResponses sr " +
            "LEFT JOIN SurveyInformation si " +
            "ON sr.questionId = si.id " +
            "WHERE sr.partyId = :partyId " +
            "AND sr.electionId = :electionId")
    ArrayList<SurveyResponses> findAllByCustomDetails(String partyId, String electionId);
}
