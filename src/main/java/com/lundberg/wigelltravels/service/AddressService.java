package com.lundberg.wigelltravels.service;

import com.lundberg.wigelltravels.dto.AddressDto;
import com.lundberg.wigelltravels.entity.Address;
import com.lundberg.wigelltravels.entity.Customer;
import com.lundberg.wigelltravels.exception.CustomerNotFoundException;
import com.lundberg.wigelltravels.repository.AddressRepository;
import com.lundberg.wigelltravels.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class AddressService {

    private final AddressRepository addressRepository;
    private final CustomerRepository customerRepository;

    public AddressService(AddressRepository addressRepository, CustomerRepository customerRepository) {
        this.addressRepository = addressRepository;
        this.customerRepository = customerRepository;
    }

    private static final Logger logger = LoggerFactory.getLogger(AddressService.class);

    public AddressDto createAddress(Long customerId, AddressDto dto) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
        Address address = new Address();
        address.setStreet(dto.street());
        address.setCity(dto.city());
        address.setCustomer(customer);
        Address saved = addressRepository.save(address);
        logger.info("Created address for customer {}", customerId);
        return new AddressDto(
                saved.getId(),
                saved.getStreet(),
                saved.getCity()
        );
    }

    public void deleteAddress(Long customerId, Long addressId){
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found"));
        if (!address.getCustomer().getId().equals(customerId)){
            throw new IllegalArgumentException("Address does not belong to this customer");
        }

        addressRepository.delete(address);
        logger.info("Deleted address with id {} for customer {}", addressId, customerId);
    }
}