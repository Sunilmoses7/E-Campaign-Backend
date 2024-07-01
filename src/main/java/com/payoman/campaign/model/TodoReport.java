package com.payoman.campaign.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import java.sql.Timestamp;

import static javax.persistence.GenerationType.SEQUENCE;

@Data
@Entity
@NoArgsConstructor
public class TodoReport {
    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "seqGen")
    @SequenceGenerator(name = "seqGen", sequenceName = "seq", initialValue = 1)
    private Integer id;

    private String task;

    private String taskDescription;

    private String taskType;

    private Boolean isCompleted;

    private String boothAgentNumber;

    private String partyId;

    private String electionId;

    private String assemblyConstituencyNumber;

    @Type(type = "timestamp")
    private Timestamp created_at;
}
