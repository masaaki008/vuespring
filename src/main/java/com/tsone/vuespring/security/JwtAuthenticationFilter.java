package com.tsone.vuespring.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tsone.vuespring.form.UserForm;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;

        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/api/login", "POST"));
        this.setUsernameParameter("username");
        this.setPasswordParameter("password");

        this.setAuthenticationSuccessHandler((req, res, ex) -> {
            String token = JWT.create()
                .withIssuer("com.tsone")
                .withClaim("username", ex.getName())
                .sign(Algorithm.HMAC256("secret"));
            res.setHeader("X-AUTH-TOKEN", token);
            res.setStatus(200);
        });

        this.setAuthenticationFailureHandler((req, res, ex) -> {
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        });
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
        HttpServletResponse response) throws AuthenticationException {
        try {
            ServletInputStream stream = request.getInputStream();
            UserForm form = new ObjectMapper().readValue(request.getInputStream(), UserForm.class);
            return this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(form.getMailAddress(), form.getPassword(), new ArrayList<>())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
