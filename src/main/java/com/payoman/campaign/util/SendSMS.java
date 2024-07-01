package com.payoman.campaign.util;

import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SendSMS {

    public boolean sendMessage(String phone, String message) {
        log.info("Sending OTP message to " + phone + " ...");
        StringBuffer sb = new StringBuffer();
        sb.append("https://")
                .append(Constants.API_ID)
                .append(":")
                .append(Constants.API_TOKEN)
                .append(Constants.SUBDOMAIN)
                .append("/v1/Accounts/")
                .append(Constants.ACCOUNT_SID)
                .append("/Sms/send");


        OkHttpClient client = new OkHttpClient().newBuilder().build();
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("From", Constants.SENDER_ID)
                .addFormDataPart("To", phone)
                .addFormDataPart("Body", message).build();

        String credentials = Credentials.basic(Constants.API_ID, Constants.API_TOKEN);
        Request request = new Request.Builder()
                .url(sb.toString()).method("POST", body)
                .addHeader("Authorization", credentials).addHeader("Content-Type", "multipart/form-data").build();
        try {
            Response response = client.newCall(request).execute();
            int status = response.code();
            if (status == 200) {
                return true;
            }
        } catch (Exception error) {
            error.printStackTrace();
        }
        return false;
    }
}
