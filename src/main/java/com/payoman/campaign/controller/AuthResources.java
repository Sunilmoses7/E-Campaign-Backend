package com.payoman.campaign.controller;

import com.payoman.campaign.config.JwtTokenHelper;
import com.payoman.campaign.model.BoothAgent;
import com.payoman.campaign.model.BoothAgentActivity;
import com.payoman.campaign.payload.OtpResponse;
import com.payoman.campaign.service.BoothAgentActivityService;
import com.payoman.campaign.service.BoothAgentService;
import com.payoman.campaign.service.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;

@RestController
@RequestMapping("/auth")
public class AuthResources {

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    private OtpService otpService;

    @Autowired
    private BoothAgentActivityService boothAgentActivityService;

    @Autowired
    private BoothAgentService boothAgentService;


    @PostMapping("/otp")
    public ResponseEntity<Object> createEntity(@RequestParam(value = "phone") Long phone,
                                               @RequestParam(value = "src", required = false) String src) {
        if (boothAgentService.isEntityAvailable(phone, src)) {
            otpService.generateOtp(phone.toString());
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body("{\"status\":\"SUCCESS\"}");
        }
        return ResponseEntity.internalServerError().contentType(MediaType.APPLICATION_JSON).body("{\"status\":\"FAILED\"," +
                "\"message\":\"User not allowed to login, Please contact administrator for support.\"}");
    }

    @PostMapping("/v-otp")
    public ResponseEntity<Object> verifyOtpAndGenerateToken(@RequestParam("phone") Long phone,
                                                            @RequestParam("otp") String otp,
                                                            @RequestParam(value = "fcm", required = false) String fcm_token) {
        if (boothAgentService.isEntityAvailable(phone, "") && otpService.verifyOtp(phone.toString(), otp)) {
            boothAgentActivityService.createEntity(new BoothAgentActivity(phone.toString(), "Verified OTP and generated token", "LOGIN", new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis())));

            if (fcm_token != null)
                boothAgentService.updateDetails(phone, fcm_token);

            BoothAgent boothAgent = boothAgentService.getEntityDetails(phone);
            return ResponseEntity.ok().body(new OtpResponse(jwtTokenHelper.generateToken(phone.toString()), boothAgent.getPartNo(), boothAgent.getAssemblyConstituencyNumber(), boothAgent.getName(), boothAgent.getPartyId(), boothAgent.getElectionId()));
        }
        return ResponseEntity.internalServerError().contentType(MediaType.APPLICATION_JSON).body("{\"status\":\"FAILED\"," +
                "\"message\":\"User not allowed to login, Please contact administrator for support or Otp verification failed.\"}");
    }

    @PostMapping("/token")
    public ResponseEntity<Object> generateToken(@RequestParam(value = "phone") Long phone) {
        if (boothAgentService.isEntityAvailable(phone, "")) {
            boothAgentActivityService.createEntity(new BoothAgentActivity(phone.toString(), "Generated refresh token", "REFRESH_TOKEN", new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis())));
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body("{\"token\" : \"" + jwtTokenHelper.generateToken(phone.toString()) + "\"}");
        }
        return ResponseEntity.internalServerError().contentType(MediaType.APPLICATION_JSON).body("{\"status\":\"FAILED\"," +
                "\"message\":\"User not allowed login, Please contact administrator for support\"}");
    }


}
