package com.tsone.vuespring.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import java.util.Calendar;
import java.util.Date;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * JWT用設定値
 * @author
 */
@Data
@Component
@ConfigurationProperties(prefix = "pn.jwt")
public class JwtProperties {

    /**
     * タイムアウト時間(分)
     */
    private int timeout;

    /**
     * 発行者
     */
    private String issuer;

    /**
     * シークレットキー
     */
    private String key;

    /**
     * JWTトークン生成処理
     *
     * @param userName
     * @return
     */
    public String createJwtToken(String userName) {
        Date timeout = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(timeout);
        calendar.add(Calendar.MINUTE, this.timeout);
        timeout = calendar.getTime();
        return JWT.create()
            .withIssuer(this.issuer)
            .withExpiresAt(timeout)
            .withClaim("username", userName)
            .sign(Algorithm.HMAC256(this.key));
    }
}
