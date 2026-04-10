package com.lundberg.wigelltravels.controller;

import com.lundberg.wigelltravels.dto.AddressDto;
import com.lundberg.wigelltravels.dto.CustomerCreateDto;
import com.lundberg.wigelltravels.dto.CustomerResponseDto;
import com.lundberg.wigelltravels.entity.Address;
import com.lundberg.wigelltravels.entity.Customer;
import com.lundberg.wigelltravels.service.AddressService;
import com.lundberg.wigelltravels.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers") // /customers kan vara med i denna sökväg eftersom jag annars behöver ange sen i varje metod
public class CustomerController {

    private final CustomerService customerService;
    private final AddressService addressService;

    public CustomerController (CustomerService customerService, AddressService addressService){
        this.customerService = customerService;
        this.addressService = addressService;
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponseDto>> getAllCustomers(){
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    @PostMapping
    public ResponseEntity<CustomerResponseDto> createCustomer(@Valid @RequestBody CustomerCreateDto dto){
        return ResponseEntity.status(201).body(customerService.createCustomer(dto));
    }

    @DeleteMapping("{customerId}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long customerId){
        customerService.deleteCustomer(customerId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<CustomerResponseDto> updateCustomer(
            @PathVariable Long customerId,
            @Valid @RequestBody CustomerCreateDto dto) {

        return ResponseEntity.ok(
                customerService.updateCustomer(customerId, dto)
        );
    }

    // ADD ADDRESS
    @PostMapping("/{customerId}/addresses")
    public ResponseEntity<AddressDto> createAddress(
            @PathVariable Long customerId,
            @RequestBody AddressDto dto) {

        return ResponseEntity.status(201)
                .body(addressService.createAddress(customerId, dto));
    }

    // DELETE ADDRESS
    @DeleteMapping("/{customerId}/addresses/{addressId}")
    public ResponseEntity<Void> deleteAddress(
            @PathVariable Long customerId,
            @PathVariable Long addressId) {

        addressService.deleteAddress(customerId, addressId);
        return ResponseEntity.noContent().build();
    }

}