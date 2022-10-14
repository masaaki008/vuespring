package com.tsone.vuespring.security;

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
}
