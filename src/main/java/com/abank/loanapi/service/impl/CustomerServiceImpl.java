package com.abank.loanapi.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abank.loanapi.exception.ResourceNotFoundException;
import com.abank.loanapi.model.entity.Customer;
import com.abank.loanapi.repository.CustomerRepository;
import com.abank.loanapi.service.CustomerService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public Customer findCustomerById(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Customer not found with the id: " + customerId));

    }

}
