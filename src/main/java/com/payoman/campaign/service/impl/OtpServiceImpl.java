package com.payoman.campaign.service.impl;

import com.payoman.campaign.model.Otp;
import com.payoman.campaign.model.OtpSms;
import com.payoman.campaign.repo.OtpRepo;
import com.payoman.campaign.repo.OtpSmsRepo;
import com.payoman.campaign.service.OtpService;
import com.payoman.campaign.util.SendSMS;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.Random;

@Service
@Slf4j
public class OtpServiceImpl implements OtpService {

    @Autowired
    private OtpRepo otpRepo;

    @Autowired
    private SendSMS sendSMS;

    @Autowired
    private OtpSmsRepo otpSmsRepo;

    @Override
    @Async
    public Boolean generateOtp(String phone) {
        log.info("Generating OTP ...");
        String otpRandom = new DecimalFormat("0000").format(new Random().nextInt(9999));
        String msg = otpRandom + ": Is Your OTP For The Payoman Application. OTP Is Valid For 30 Minutes";
        log.info(msg);
        boolean result = sendSMS.sendMessage(phone, msg);
        if (result) {
            Otp otp = new Otp();
            otp.setOtp(otpRandom);
            otp.setCreated_at(new Timestamp(System.currentTimeMillis()));
            otp.setUpdated_at(new Timestamp(System.currentTimeMillis()));
            otp.setPhone(phone);
            otp.setExpires_at(new Timestamp(System.currentTimeMillis() + 1800000));
            otpRepo.saveAndFlush(otp);
            log.info("Saving OTP info ...");

            OtpSms otpSms = new OtpSms();
            otpSms.setSms(msg);
            otpSms.setPhone(phone);
            otpSms.setCreated_at(new Timestamp(System.currentTimeMillis()));
            otpSms.setUpdated_at(new Timestamp(System.currentTimeMillis()));
            otpSmsRepo.saveAndFlush(otpSms);
            log.info("Saving Otp SMS info ...");

        }
        return result;
    }

    @Override
    public boolean verifyOtp(String phone, String otp) {
        log.info("Verifying OTP ...");
        Otp o = otpRepo.findById(phone).orElse(null);
        if (o != null && o.getOtp().equals(otp) && o.getExpires_at().after(new Timestamp(System.currentTimeMillis())))
            return true;
        return false;
    }
}
