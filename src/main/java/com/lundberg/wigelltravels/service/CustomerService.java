package com.lundberg.wigelltravels.service;

import com.lundberg.wigelltravels.dto.CustomerCreateDto;
import com.lundberg.wigelltravels.dto.CustomerResponseDto;
import com.lundberg.wigelltravels.entity.Customer;
import com.lundberg.wigelltravels.exception.CustomerNotFoundException;
import com.lundberg.wigelltravels.repository.CustomerRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    public CustomerService(CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);

    public CustomerResponseDto createCustomer(CustomerCreateDto dto){
        Customer customer = new Customer();
        customer.setName(dto.name());
        customer.setUsername(dto.username());
        String encodePassword = passwordEncoder.encode(dto.password());
        customer.setPassword(passwordEncoder.encode(dto.password()));
        customer.setRole("ROLE_USER");
        Customer saved = customerRepository.save(customer);
        logger.info("Created customer with id {}", saved.getId());

        return new CustomerResponseDto(
                saved.getId(),
                saved.getName(),
                saved.getUsername()
        );
    }

    public List<CustomerResponseDto> getAllCustomers() {

        return customerRepository.findAll().stream()
                .map(customer -> new CustomerResponseDto(
                        customer.getId(),
                        customer.getName(),
                        customer.getUsername()
                ))
                .toList();
    }

    public CustomerResponseDto updateCustomer(Long customerId, CustomerCreateDto dto) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
        customer.setName(dto.name());
        customer.setUsername(dto.username());
        customer.setPassword(passwordEncoder.encode(dto.password()));
        Customer updated = customerRepository.save(customer);
        logger.info("Updated customer with id {}", customerId);
        return new CustomerResponseDto(
                updated.getId(),
                updated.getName(),
                updated.getUsername()
        );
    }

    public void deleteCustomer(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
        customerRepository.delete(customer);
        logger.info("Deleted customer with id {}", customerId);
    }
}
