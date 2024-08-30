package com.example.fms.service;

import com.example.fms.entity.Administrator;
import com.example.fms.repository.AdministratorRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class InternalUserDetailsService implements UserDetailsService {

    private final AdministratorRepository administratorRepository;

    public InternalUserDetailsService(AdministratorRepository administratorRepository) {
        this.administratorRepository = administratorRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Administrator admin = administratorRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("user not found: " + username));

        GrantedAuthority authority = new SimpleGrantedAuthority(admin.getRole());
        return new org.springframework.security.core.userdetails.User(
                admin.getUsername(),
                admin.getPassword(),
                Collections.singletonList(authority)
        );
    }
}
