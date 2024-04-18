package com.dreamcoder.oauthserver.config;

import com.dreamcoder.oauthserver.service.CustomAuthenticationProvider;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@EnableWebSecurity
@Configuration
public class SpringSecurityConfig {


    @Autowired
    private CustomAuthenticationProvider authenticationProvider;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity.authorizeHttpRequests(auth -> {
                    auth.anyRequest().authenticated();
                })
                .formLogin(withDefaults())
                .build();
    }


    @Autowired
    public void bindAuthenticationProvider(AuthenticationManagerBuilder managerBuilder) {
        managerBuilder.authenticationProvider(authenticationProvider);
    }


}
