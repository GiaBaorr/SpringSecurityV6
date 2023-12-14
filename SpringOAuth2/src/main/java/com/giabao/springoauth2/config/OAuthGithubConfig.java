package com.giabao.springoauth2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class OAuthGithubConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(customizer -> customizer.anyRequest().authenticated())
                .oauth2Login(Customizer.withDefaults());
        return http.build();
    }


    //No need to config these beans if we define some necessary properties inside application.properties
    //Spring will automatically init these beans
    /*@Bean
    public ClientRegistrationRepository clientRegistrationRepository(){
        ClientRegistration clientRegistration = clientRegistration();
        return new InMemoryClientRegistrationRepository(clientRegistration);
    }
    private ClientRegistration clientRegistration(){
        return CommonOAuth2Provider
                .GITHUB
                .getBuilder("github")
                .clientId("dab5c081e57f904e3522")
                .clientSecret("0d9e5eef7fc73d9609e155523cf7192b65c41f55")
                .build();
    }*/


}
