package com.tsone.vuespring.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * ログインフィルター
 *
 * @author
 */
@Slf4j
public class LoginFilter extends OncePerRequestFilter {

    private JwtProperties jwtProperties;

    public LoginFilter(JwtProperties jwtProperties) {
        super();
        this.jwtProperties = jwtProperties;
    }

    /**
     * JWTチェックフィルター
     *
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

        try {
            String header = request.getHeader("X-AUTH-TOKEN");
            log.debug("X-AUTH-TOKEN: {}", header);

            if (StringUtils.isEmpty(header) || !header.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }
            String token = header.substring(7);
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(this.jwtProperties.getKey())).build().verify(token);
            String username = decodedJWT.getClaim("username").asString();

            // JWTトークンタイムアウト更新
            String reToken = this.jwtProperties.createJwtToken(username);
            response.setHeader("X-AUTH-TOKEN", reToken);

            SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>()));
            filterChain.doFilter(request, response);
        } catch (JWTVerificationException e) {
            log.warn("JWT Tokenチェックエラー : {}", e.getMessage());
            filterChain.doFilter(request, response);
        }
    }
}
