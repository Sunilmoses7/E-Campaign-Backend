package com.payoman.campaign.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class BoothAgent {
    @Id
    private Long boothAgentPhoneNumber;

    private String partNo;

    private String name;

    private String assemblyConstituencyNumber;

    @Type(type = "timestamp")
    private Timestamp created_at;

    private String fcmToken;

    private String partyId;

    private String electionId;

    private Boolean isAdmin;

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at() {
        this.created_at = new Timestamp(System.currentTimeMillis());
    }
}