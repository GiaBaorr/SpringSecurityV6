package com.giabao.securityv6.controller;

import com.giabao.securityv6.model.Customer;
import com.giabao.securityv6.model.Loans;
import com.giabao.securityv6.repository.CustomerRepository;
import com.giabao.securityv6.repository.LoansRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LoansController {

    @Autowired
    private LoansRepository loanRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/myLoans")
    //@PostAuthorize("hasAnyRole('USER')")
    public List<Loans> getLoanDetails(@RequestParam String email) {
        List<Customer> customers = customerRepository.findCustomersByEmail(email);
        int id = customers.get(0).getId();

        List<Loans> loans = loanRepository.findByCustomerIdOrderByStartDtDesc(id);
        if (loans != null) {
            return loans;
        } else {
            return null;
        }
    }
}
