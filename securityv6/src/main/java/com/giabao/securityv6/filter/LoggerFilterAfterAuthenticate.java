package com.giabao.securityv6.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.util.logging.Logger;

public class LoggerFilterAfterAuthenticate implements Filter {

    private final Logger LOGGER = Logger.getLogger(LoggerFilterAfterAuthenticate.class.getName());

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null){
            LOGGER.info("User " + authentication.getName()+"\n" +
                    "Authorities " + authentication.getAuthorities().toString());
        }
        filterChain.doFilter(request,response);
    }
}
