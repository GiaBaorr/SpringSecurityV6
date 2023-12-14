package com.giabao.springoauth2.controller;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecureController {

    @GetMapping("/")
    public String main(OAuth2AuthenticationToken token){
        System.out.println(token.getPrincipal());
        return "secure.html";
    }
}
