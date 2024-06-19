package com.learning.OnlineLearning.Configurations;

import com.learning.OnlineLearning.Filters.JwtFilter;
import com.learning.OnlineLearning.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

    @Autowired
    JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((request)->
                            request.requestMatchers("user/**").permitAll()
                                    .anyRequest().authenticated());
          httpSecurity.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
          httpSecurity.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

//    @Bean
//    public FilterRegistrationBean<JwtFilter> tenantFilterRegistration(JwtFilter filter) {
//        FilterRegistrationBean<JwtFilter> registration = new FilterRegistrationBean<>(filter);
//        registration.setEnabled(false);
//        return registration;
//    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ProviderManager providerManager(UserService userService){
        DaoAuthenticationProvider dao=new DaoAuthenticationProvider();
        dao.setPasswordEncoder(passwordEncoder());
        dao.setUserDetailsService(userService);
        return new ProviderManager(dao);
    }

//    @Bean
//    public UserDetailsManager userDetailsManager(){
//        return new InMemoryUserDetailsManager(User.builder()
//                                                .username("amir")
//                                                .password(  passwordEncoder().encode("amir"))
//                .build());
//    }




}
