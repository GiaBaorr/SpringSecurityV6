package com.giabao.securityv6.repository;

import com.giabao.securityv6.model.Loans;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface LoansRepository extends JpaRepository<Loans,Integer> {

    //@PreAuthorize("hasRole('USER')")
    List<Loans> findByCustomerIdOrderByStartDtDesc(int customerId);
}
