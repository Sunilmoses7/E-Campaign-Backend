package com.payoman.campaign.model;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import java.sql.Timestamp;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Data
public class OtpSms {

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "seqGen")
    @SequenceGenerator(name = "seqGen", sequenceName = "seq", initialValue = 1)
    private Integer id;

    private String sms;

    private String phone;

    @Type(type = "timestamp")
    private Timestamp created_at;

    @Type(type = "timestamp")
    private Timestamp updated_at;

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at() {
        this.created_at = new Timestamp(System.currentTimeMillis());
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at() {
        this.updated_at = new Timestamp(System.currentTimeMillis());
    }
}
