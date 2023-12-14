package com.giabao.securityv6.controller;

import com.giabao.securityv6.model.Contact;
import com.giabao.securityv6.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
public class ContactController {

    @Autowired
    private ContactRepository contactRepository;

    @PostMapping("/contact")
    //@PreFilter("filterObject.contactName != 'Test'")//PreFilter and PostFilter only accept interface type
    //@PostFilter("filterObject.contactName != 'Test'") //the contact still be saved, but controller not return
    public List<Contact> saveContactInquiryDetails(@RequestBody List<Contact> contacts) {
        Contact contact = contacts.get(0);
        contact.setContactId(getServiceReqNumber());
        contact.setCreateDt(new Date(System.currentTimeMillis()));
        contact = contactRepository.save(contact);

        List<Contact> result = new ArrayList<>();
        result.add(contact);

        return result;
    }

    public String getServiceReqNumber() {
        Random random = new Random();
        int ranNum = random.nextInt(999999999 - 9999) + 9999;
        return "SR"+ranNum;
    }
}
