package com.wide.agus.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount;
import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Component
public class AuthenticationFilter implements Filter {

    @Autowired
    @Lazy
    private JwtDecoder jwtDecoder;


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String token = extractTokenFromRequest((HttpServletRequest) request);
        if (token != null){
            Jwt jwt = jwtDecoder.decode(token);
            Map<String, Object> claims = jwt.getClaims();
            String username = (String) claims.get("preferred_username");
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null);
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails((HttpServletRequest) request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        filterChain.doFilter(request, response);
    }

//    private Authentication authenticateUser(String token) {
//        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null);
//        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//        return authenticationManager.authenticate(authenticationToken);
//    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    private String extractTokenFromRequest(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }

    private Authentication getAuthentication(HttpServletRequest request) {
        SimpleKeycloakAccount account = (SimpleKeycloakAccount) request.getUserPrincipal();
        if (account != null) {
            AccessToken principal = account.getKeycloakSecurityContext().getToken();
            return new UsernamePasswordAuthenticationToken(principal, null, null);
        }
        return null;
    }
}
