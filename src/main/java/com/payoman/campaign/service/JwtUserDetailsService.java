package com.payoman.campaign.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private BoothAgentService boothAgentService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (boothAgentService.isEntityAvailable(Long.parseLong(username),"")) {
            return new User(username, "pass", new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User not found with phone : " + username);
        }
    }
}