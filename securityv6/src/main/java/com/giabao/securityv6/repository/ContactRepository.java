package com.giabao.securityv6.repository;

import com.giabao.securityv6.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact,String> {

}
