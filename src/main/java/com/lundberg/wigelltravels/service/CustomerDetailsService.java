package com.lundberg.wigelltravels.service;

import com.lundberg.wigelltravels.entity.Customer;
import com.lundberg.wigelltravels.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomerDetailsService implements UserDetailsService {

    private final CustomerRepository customerRepository;

    public CustomerDetailsService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    private static final Logger logger = LoggerFactory.getLogger(CustomerDetailsService.class);

    @Override
    public UserDetails loadUserByUsername(String username){
        Customer customer = customerRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        logger.info("Loading user with username {}", username);

        return org.springframework.security.core.userdetails.User
                .withUsername(customer.getUsername())
                .password(customer.getPassword())
                .roles(customer.getRole().replace("ROLE_", ""))
                .build();
    }
}
