package com.payoman.campaign.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OtpResponse {
    private String token;
    private String partNos;
    private String assemblyConstituencyNumber;
    private String boothAgentName;
    private String partyId;
    private String electionId;
}
