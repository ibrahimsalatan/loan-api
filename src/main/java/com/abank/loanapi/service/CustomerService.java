package com.abank.loanapi.service;

import com.abank.loanapi.model.entity.Customer;

public interface CustomerService {

    public Customer findCustomerById(Long cusomerId);
}
