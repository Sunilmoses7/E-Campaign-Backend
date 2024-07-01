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
public class BoothAgentActivity {

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "seqGen")
    @SequenceGenerator(name = "seqGen", sequenceName = "seq", initialValue = 1)
    private Integer id;

    private String boothAgentPhone;

    private String tasksInfo;

    private String taskType;

    @Type(type = "timestamp")
    private Timestamp created_at;

    @Type(type = "timestamp")
    private Timestamp updated_at;

    private String partyId;

    private String electionId;

    public BoothAgentActivity(String boothAgentPhone, String tasksInfo, String taskType, Timestamp created_at, Timestamp updated_at) {
        this.boothAgentPhone = boothAgentPhone;
        this.tasksInfo = tasksInfo;
        this.taskType = taskType;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public BoothAgentActivity(String boothAgentPhone, String tasksInfo, String taskType, Timestamp created_at, Timestamp updated_at, String partyId, String electionId) {
        this.boothAgentPhone = boothAgentPhone;
        this.tasksInfo = tasksInfo;
        this.taskType = taskType;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.partyId = partyId;
        this.electionId = electionId;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at() {
        this.created_at = new Timestamp(System.currentTimeMillis());
    }
}
