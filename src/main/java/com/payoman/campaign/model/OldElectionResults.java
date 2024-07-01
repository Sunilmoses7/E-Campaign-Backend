package com.payoman.campaign.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import java.sql.Timestamp;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Data
public class OldElectionResults {

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "seqGen")
    @SequenceGenerator(name = "seqGen", sequenceName = "seq", initialValue = 1)
    private Integer id;

    private String mpConstituencyNumber;
    private String electionType;
    private String assemblyConstituencyNumber;
    private String assemblyConstituencyName;
    private String partNo;
    private String partyName;
    private String candidateName;
    private String electionYear;
    private Integer numberOfVotes;
    private Timestamp createdAt;
}