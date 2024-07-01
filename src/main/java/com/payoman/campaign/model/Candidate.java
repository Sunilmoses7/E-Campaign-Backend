package com.payoman.campaign.model;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

import static javax.persistence.GenerationType.SEQUENCE;

@Data
@Entity
public class Candidate {
    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "seqGen")
    @SequenceGenerator(name = "seqGen", sequenceName = "seq", initialValue = 1)
    private Integer id;

    @Column(unique = true)
    private String candidatePhoneNumber;

    private String candidateName;
    private Integer partyId;
    private String assemblyConstituencyNumber;
    private String candidateImageUrl;
    private String partySymbolUrl;
    private String candidateManifestoUrl;
    private ArrayList<String> bannerUrls;

    private ArrayList<String> newsLetterUrls; //URL UPDATE API
    private HashMap<String, String> urls; //URL UPDATE API

    @Type(type = "timestamp")
    private Timestamp created_at;

    private String constituencyName;
    private String education;
    private String designation;
    private String candidateWebsite;
    private String electionId;

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at() {
        this.created_at = new Timestamp(System.currentTimeMillis());
    }
}
