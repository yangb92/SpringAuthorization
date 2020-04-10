package com.yangb.security.uaa.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.InMemoryAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Arrays;

/**
 * @author Created by yangb on 2020/4/9
 */
@Configuration
public class TokenConfig {

    private String SIGN_KEY = "123";

    @Resource
    private TokenStore tokenStore;
    @Resource
    private JwtAccessTokenConverter accessTokenConverter;
    @Resource
    private PasswordEncoder passwordEncoder;

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


    // 配置授权码服务
    @Bean
    public AuthorizationCodeServices authorizationCodeServices(DataSource dataSource) {
        // 设置授权码模式的授权码如何存取,暂时采用内存方式
//        return new InMemoryAuthorizationCodeServices();
        return new JdbcAuthorizationCodeServices(dataSource);
    }


    // 令牌管理服务
    @Bean
    @DependsOn({"tokenStore","accessTokenConverter"})
    public AuthorizationServerTokenServices tokenServices(ClientDetailsService clientDetailsService) {
        DefaultTokenServices services = new DefaultTokenServices();
        services.setClientDetailsService(clientDetailsService); // 客户端详情服务
        services.setSupportRefreshToken(true); // 支持令牌刷新
        services.setTokenStore(tokenStore); // 令牌存储策略
        // 令牌增强
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(accessTokenConverter));
        services.setTokenEnhancer(tokenEnhancerChain);
        services.setAccessTokenValiditySeconds(7200); // 令牌有效期2小时
        services.setRefreshTokenValiditySeconds(259200); // 刷新令牌默认有效期3天
        return services;
    }

    // 客户端信息配置到数据库
    @Bean
    public ClientDetailsService clientDetailsService(DataSource dataSource) {
        ClientDetailsService clientDetailsService = new JdbcClientDetailsService(dataSource);
        ((JdbcClientDetailsService) clientDetailsService).setPasswordEncoder(passwordEncoder);
        return clientDetailsService;
    }
}
