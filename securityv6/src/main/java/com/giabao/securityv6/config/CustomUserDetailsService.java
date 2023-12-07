/*
package com.giabao.securityv6.config;

import com.giabao.securityv6.model.Customer;
import com.giabao.securityv6.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
//user detail service used in DAOAuthenticationProvider,
//which is no more needed because we implements out provider

public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private CustomerRepository customerRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        List<Customer> customerList = customerRepository.findCustomersByEmail(username);

        if(customerList.isEmpty()){
            throw new UsernameNotFoundException("User details not found for the user " + username);
        }

        String email = customerList.get(0).getEmail();
        String password = customerList.get(0).getPassword();
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(customerList.get(0).getRole()));

        return new User(email,password,authorities);
    }
}
*/
