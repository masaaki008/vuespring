package com.tsone.vuespring.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tsone.vuespring.dto.MUserDto;
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

/**
 * JWT認証フィルター
 *
 * @author
 */
@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    /**
     * フィルター
     *
     * @param authenticationManager
     */
    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;

        // ログインパスの設定
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/api/login", "POST"));
        this.setUsernameParameter("username");
        this.setPasswordParameter("password");

        // 認証成功時
        this.setAuthenticationSuccessHandler((req, res, ex) -> {
            LoginUser loginUser = (LoginUser) ex.getPrincipal();
            MUserDto user = loginUser.getUser();

            // トークン生成処理
            String token = JWT.create()
                .withIssuer("com.tsone")
                .withClaim("username", ex.getName())
                .sign(Algorithm.HMAC256("secret"));

            // ヘッダーにX-AUTH-TOKENをセット
            res.setHeader("X-AUTH-TOKEN", token);

            // ログインユーザ情報をセット
            res.setContentType("text/json;charset=UTF-8");
            res.getWriter().write(user.toJsonString());

            // レスポンスステータス
            res.setStatus(HttpServletResponse.SC_OK);
        });

        // 認証失敗時
        this.setAuthenticationFailureHandler((req, res, ex) -> {

            // レスポンスステータス
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        });
    }

    /**
     * 認証処理
     *
     * @param request
     * @param response
     * @return UsernamePasswordAuthenticationToken
     * @throws AuthenticationException
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
        HttpServletResponse response) throws AuthenticationException {
        try {
            ServletInputStream stream = request.getInputStream();

            // リクエストのjsonをマッピング
            UserForm form = new ObjectMapper().readValue(request.getInputStream(), UserForm.class);
            return this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(form.getMailAddress(), form.getPassword(), new ArrayList<>())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
