package com.yangb.security.order.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * @author Created by yangb on 2020/4/9
 */
@Configuration
public class TokenConfig {

    private String SIGN_KEY = "123";

    @Bean
    public TokenStore tokenStore() {
        // JWT 令牌存储方案
        return new JwtTokenStore(accessTokenConverter());
    }

    // 令牌转换器
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(SIGN_KEY); // 对称密匙
        return converter;
    }
}
