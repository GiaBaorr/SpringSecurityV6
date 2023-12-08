package com.giabao.securityv6.config;

import com.giabao.securityv6.model.Authority;
import com.giabao.securityv6.model.Customer;
import com.giabao.securityv6.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        List<Customer> customerList = customerRepository.findCustomersByEmail(username);
        if (!customerList.isEmpty()) {
            Customer fetchedCustomer = customerList.get(0);
            if (passwordEncoder.matches(password, fetchedCustomer.getPwd())) {
                return new UsernamePasswordAuthenticationToken(
                        username, password,
                        extractAuthorites(fetchedCustomer.getAuthorities()));
            } else {
                throw new BadCredentialsException("Invalid password");
            }
        } else {
            throw new BadCredentialsException("No user registered with this details");
        }
    }

    //helper method to generate list of granted authorities from  Set<Authority>
    private List<GrantedAuthority> extractAuthorites(Set<Authority> authorities) {
        List<GrantedAuthority> result = new ArrayList<>();
        for (Authority auth : authorities) {
            result.add(new SimpleGrantedAuthority(auth.getName()));
        }
        return result;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
