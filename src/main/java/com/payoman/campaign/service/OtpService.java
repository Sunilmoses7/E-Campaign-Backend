package com.payoman.campaign.service;

public interface OtpService {
    Boolean generateOtp(String phone);

    boolean verifyOtp(String phone, String otp);
}
