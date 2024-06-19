package com.learning.OnlineLearning.Filters;

import com.learning.OnlineLearning.Entities.User;
import com.learning.OnlineLearning.JwtUtities.JwtService;
import com.learning.OnlineLearning.Services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    JwtService jwtService;

    @Autowired
    UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

//        System.out.println(request.getContextPath());
//        System.out.println(request.getPathInfo());
        if(request.getServletPath().startsWith("/user/")){
            filterChain.doFilter(request,response);
            return;
        };


        System.out.println("JwtFilter start");
        String bearer =request.getHeader("Authorization");

        if(bearer != null && bearer.startsWith("Bearer")){
            String jwt = bearer.substring(7);
            String email = jwtService.getSubject(jwt);

            UserDetails userFound =userService.loadUserByUsername(email);
            if(!jwtService.isExpired(jwt)) {
                UsernamePasswordAuthenticationToken user =
                        new UsernamePasswordAuthenticationToken(userFound.getUsername(),
                                userFound.getPassword(),
                                userFound.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(user);
                System.out.println("JwtFilter end");

                filterChain.doFilter(request, response);
                return;
            }
        }
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("Unauthorized");

    }
}
