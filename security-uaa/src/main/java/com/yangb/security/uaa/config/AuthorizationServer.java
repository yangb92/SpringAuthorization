package com.yangb.security.uaa.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import javax.annotation.Resource;

/**
 * Created by yangb on 2020/4/9
 */
@Configuration
@EnableAuthorizationServer
@DependsOn({"tokenConfig"})
public class AuthorizationServer extends AuthorizationServerConfigurerAdapter {

    @Resource
    private AuthorizationCodeServices authorizationCodeServices;
    @Resource
    private AuthenticationManager authenticationManager;
    @Resource
    private AuthorizationServerTokenServices tokenServices;
    @Resource
    private ClientDetailsService clientDetailsService;

    // 客户端详情服务
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetailsService);
        /*
        // 在内存中配置客户端
        clients.inMemory()
                .withClient("c1")
                .secret(new BCryptPasswordEncoder().encode("secret")) //客户端秘钥
                .resourceIds("res1", "res2") //可访问资源列表
                .authorizedGrantTypes("authorization_code", "password", "client_credentials", "implicit", "refresh_token") //运行授权的类型
                .scopes("all") //允许授权的范围
                .autoApprove(false) // false 跳转到授权页面
                .redirectUris("http://www.baidu.com"); //授权回调地址
        */
    }

    // 令牌访问端点
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager) //认证管理器
                .authorizationCodeServices(authorizationCodeServices) // 授权码服务
                .tokenServices(tokenServices) // 令牌管理服务
                .allowedTokenEndpointRequestMethods(HttpMethod.POST);
    }

    // 令牌访问安全约束
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                .tokenKeyAccess("permitAll()")  //oauth/token_key 是公开
                .checkTokenAccess("permitAll()")    //oauth/check_token 公开
                .allowFormAuthenticationForClients(); //表单认证

    }

}
