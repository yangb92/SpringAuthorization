# Spring Security OAuth2.0 JWT

使用 JWT 令牌

## 授权码模式

最为安全

1. 资源拥有者打开客户端,客户端要求资源拥有者给予授权,它将被浏览器重定向到服务器,重定向时会附加客户端身份信息.
```
/uaa/oauth/authorize?client_id=c1&response_type=code&scope=all&redirect_url=http://www.baidu.com/
```
>参数:
* client_id: 客户端标识
* response_type: 授权码模式固定位code
* scope:客户端权限
* redirect: 跳转url,授权码申请成功后跳转到此地址,并在后面带上code参数(授权码) 和配置客户端跳转url一致

2. 浏览器重定向授权服务器授权页面,之后用户将同意授权
3. 授权服务器授权码(AuthorizationCode)带到url参数上发送给client(通过redirect_uri)
4. 客户端拿着授权码向授权服务器索要访问access_token
```
/uaa/oauth/token?client_id=c1&client_secret=secret&grant_type=authorization_code&code=5pgxfcD&redirect_uri=http://www.baidu.com/
```

参数:
* cilent_id: 客户端标识
* secret: 客户端密匙
* grant_type: 授权类型
* code: 授权码, 注意:授权码只使用一次就无效了.
* redirect_uri: 和配置客户端跳转url一致

5. 服务器返回授权码

```json
{
    "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsicmVzMSIsInJlczIiXSwidXNlcl9uYW1lIjoibGlzaSIsInNjb3BlIjpbImFsbCJdLCJleHAiOjE1ODY0MzE1ODYsImF1dGhvcml0aWVzIjpbInAxIl0sImp0aSI6IjlmNzAwMjU3LWM4MWUtNDU3MC1hYjU0LWVjMjU1N2JhYjgyNyIsImNsaWVudF9pZCI6ImMxIn0.8QUpIuvZrM2QjfnnR4hxUGMf7nznlxKtouYP8kC6HnI",
    "token_type": "bearer",
    "refresh_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsicmVzMSIsInJlczIiXSwidXNlcl9uYW1lIjoibGlzaSIsInNjb3BlIjpbImFsbCJdLCJhdGkiOiI5ZjcwMDI1Ny1jODFlLTQ1NzAtYWI1NC1lYzI1NTdiYWI4MjciLCJleHAiOjE1ODY2ODM1ODYsImF1dGhvcml0aWVzIjpbInAxIl0sImp0aSI6IjA4NDM1OGUxLWMzMjQtNGE3MS1hZTA0LTNiZjc5ZDEwMjZhNyIsImNsaWVudF9pZCI6ImMxIn0.dPDDPuQAI0FR35V2nVzKOam8CqshOm6udIH2LHvitR4",
    "expires_in": 7199,
    "scope": "all",
    "jti": "9f700257-c81e-4570-ab54-ec2557bab827"
}
```

## 简化模式

1. 资源拥有者打开客户端,要求资源拥有者给与授权,它将被浏览器重定向到授权服务器, 重定向时会附加客户端信息.
```url
/uaa/oauth/authorize?client_id=c1&response_type=token&scope=all&redirect_uri=http://www.baidu.com
```
response_type=token 说明是简化模式

2. 浏览器重定向到授权服务器页面,用户同意授权
3. 授权服务器将令牌,以Hash的形式存放在重定向uri的fragment发送给浏览器.
```
https://www.baidu.com/#access_token=6b94c4c1-1803-484e-b5da-943b96266ff1&token_type=bearer&expires_in=42776
```
注: fragment http://example.com#L18 这个`L18`就是fragment的值.

一般来说,简化模式用于没有服务端的第三方单页面应用.

## 密码模式

1. 资源拥有者将用户名,密码发送给客户端.
2. 客户端拿着用户名和密码向授权服务器请求令牌
```
/uaa/oauth/token?client_id=c1&client_secret=secret&grant_type=password&username=lisi&password=123
```
3. 授权服务器将令牌发送给client]

这种模式十分简单,意味着直接将用户敏感信息泄露给了client, 因此这种模式只适用于我们自己的系统

## 客户端模式

1. 客户端向授权服务器发送自己的身份信息,并请求令牌
2. 确认客户端身份无误后,将令牌发送给clien

```
/uaa/oauth/token?client_id=c1&client_secret=secret&grant_type=client_credentials
```
