package com.giabao.securityv6.repository;

import com.giabao.securityv6.model.Cards;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardsRepository extends JpaRepository<Cards, Integer> {

    List<Cards> findByCustomerId(int customerId);
}
