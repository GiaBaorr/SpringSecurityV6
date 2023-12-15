package com.giabao.securityv6.controller;

import com.giabao.securityv6.model.Accounts;
import com.giabao.securityv6.model.Customer;
import com.giabao.securityv6.repository.AccountsRepository;
import com.giabao.securityv6.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AccountController {

    @Autowired
    private AccountsRepository accountsRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/myAccount")
    public Accounts getAccountDetails(@RequestParam String email) {
        List<Customer> customers = customerRepository.findCustomersByEmail(email);
        int id = customers.get(0).getId();

        Accounts accounts = accountsRepository.findByCustomerId(id);
        if (accounts != null) {
            return accounts;
        }
        return null;
    }


}
