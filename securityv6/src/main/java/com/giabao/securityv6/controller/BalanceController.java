package com.giabao.securityv6.controller;


import com.giabao.securityv6.model.AccountTransactions;
import com.giabao.securityv6.model.Customer;
import com.giabao.securityv6.repository.AccountTransactionsRepository;
import com.giabao.securityv6.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BalanceController {

    @Autowired
    private AccountTransactionsRepository accountTransactionsRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/myBalance")
    public List<AccountTransactions> getBalanceDetails(@RequestParam String email) {
        List<Customer> customers = customerRepository.findCustomersByEmail(email);
        int id = customers.get(0).getId();

        List<AccountTransactions> accountTransactions = accountTransactionsRepository.
                findByCustomerIdOrderByTransactionDtDesc(id);
        if (accountTransactions != null) {
            return accountTransactions;
        }
        return null;
    }

}
