package com.payoman.campaign.repo;

import com.payoman.campaign.model.OtpSms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OtpSmsRepo extends JpaRepository<OtpSms, Integer> {
}
