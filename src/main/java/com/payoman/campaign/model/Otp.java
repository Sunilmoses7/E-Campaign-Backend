package com.payoman.campaign.model;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
@Data
public class Otp {

    @Id
    private String phone;

    private String otp;

    @Type(type = "timestamp")
    private Timestamp expires_at;

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
