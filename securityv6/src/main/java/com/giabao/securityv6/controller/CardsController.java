package com.giabao.securityv6.controller;

import com.giabao.securityv6.model.Cards;
import com.giabao.securityv6.model.Customer;
import com.giabao.securityv6.repository.CardsRepository;
import com.giabao.securityv6.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CardsController {

    @Autowired
    private CardsRepository cardsRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/myCards")
    public List<Cards> getCardDetails(@RequestParam String email) {
        List<Customer> customers = customerRepository.findCustomersByEmail(email);
        int id = customers.get(0).getId();

        List<Cards> cards = cardsRepository.findByCustomerId(id);
        if (cards != null) {
            return cards;
        }
        return null;
    }
}
