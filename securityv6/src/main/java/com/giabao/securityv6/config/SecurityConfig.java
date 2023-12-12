package com.giabao.securityv6.config;


import com.giabao.securityv6.filter.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;


@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        //csrf by default will block put-post but not "get" API
        CsrfTokenRequestAttributeHandler requestHandler = new CsrfTokenRequestAttributeHandler(); //define handler
        requestHandler.setCsrfRequestAttributeName("_csrf");


        /*//Config JSESSIONID
        http.securityContext(contextConfigurer -> {
            contextConfigurer.requireExplicitSave(false);
            //let spring generate jSessionId, and save authenticationDetail in securityContextHolder
        });
        http.sessionManagement(httpSecuritySessionManagementConfigurer -> {
            httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.ALWAYS);
            //by default, it will be created when if required
            //a session will always be created if one hasn't already exist.
        });*/

        //config to stop using session, use jwt token instead, so we need should use stateless
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        //Cors config
        http.cors(corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                CorsConfiguration config = new CorsConfiguration();
                config.addAllowedOrigin("http://localhost:4200");
                config.setAllowedMethods(Collections.singletonList("*"));
                config.setAllowCredentials(true);
                config.setAllowedHeaders(Collections.singletonList("*"));
                config.setExposedHeaders(Arrays.asList("Authorization")); //enable UI side can get this header
                config.setMaxAge(3600L);
                return config; //return
            }
        }));

        //CSRF config
        http.csrf(crsfCustomizer -> {
            crsfCustomizer.csrfTokenRequestHandler(requestHandler) //set request handler for csrf
                    .ignoringRequestMatchers("/register"); //api that not need csrf protection

            crsfCustomizer.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
            //enable read csrf cookie from UI
        });

        //filter before authentication filter to reject "test" email
        http.addFilterBefore(new RequestValidationBeforeFilter(), BasicAuthenticationFilter.class);
        //execute cookie filter after basic filter
        http.addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class);
        //add logging authentication detail after logged in
        http.addFilterAfter(new LoggerFilterAfterAuthenticate(),BasicAuthenticationFilter.class);
        //filter for jwt inject
        http.addFilterAfter(new JwtFilter(), BasicAuthenticationFilter.class);
        //filter to validate jwt
        http.addFilterBefore(new JwtValidatorFilter(), BasicAuthenticationFilter.class);

        http.authorizeHttpRequests((requests) ->
                requests
                        /*.requestMatchers("/myAccount").hasAuthority("VIEWACCOUNT")
                        .requestMatchers("/myBalance").hasAnyAuthority("VIEWACCOUNT", "VIEWBALANCE")
                        .requestMatchers("/myLoans").hasAuthority("VIEWLOANS")
                        .requestMatchers("/myCards").hasAuthority("VIEWCARDS")*/
                        .requestMatchers("/myAccount").hasRole("USER")
                        .requestMatchers("/myBalance").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/myLoans").hasRole("USER")
                        .requestMatchers("/myCards").hasRole("USER")
                        .requestMatchers(
                                "/myAccount", "/myBalance", "/myLoans",
                                "/myCards", "/user")
                        .authenticated()
                        .anyRequest().permitAll());
        http.formLogin(Customizer.withDefaults());
        http.httpBasic(Customizer.withDefaults());


        return http.build();
    }

    /*@Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        UserDetails admin = User.builder()
                .username("admin")
                .password("admin")
                .authorities("admin")
                .build();
        UserDetails user = User.builder()
                .username("user")
                .password("user")
                .authorities("read")
                .build();

        return new InMemoryUserDetailsManager(admin, user);
    }*/

    /*@Bean
    public UserDetailsService jdbcUserDetailsManager(DataSource dataSource){
        return new JdbcUserDetailsManager(dataSource);
    }*/


    @Bean
    public PasswordEncoder passwordEncoder() {
        //return NoOpPasswordEncoder.getInstance();
        return new BCryptPasswordEncoder();
    }


}
