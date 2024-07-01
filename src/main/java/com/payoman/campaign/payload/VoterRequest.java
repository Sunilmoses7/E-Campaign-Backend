package com.payoman.campaign.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoterRequest implements Serializable {
    private String voterId;
    private String phoneNumber;
    private String latitude;
    private String longitude;
}
