package com.yangb.springsecurity.ctrl;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Created by yangb on 2020/4/8
 */
@Controller
public class MainController {

    @RequestMapping()
    public String index(ModelMap map){
        map.put("name", "首页");
        return "index";
    }

    @RequestMapping("/r")
    public String r(ModelMap map){
        map.put("name", "个人主页,欢迎您:"+getUsername());
        return "index";
    }

    @RequestMapping("/r/r1")
    @PreAuthorize("hasAuthority('p1')")
    public String r1(ModelMap map){
        map.put("name", "权限1页面");
        return "index";
    }

    @RequestMapping("/r/r2")
    @PreAuthorize("hasAuthority('p2')")
    public String r2(ModelMap map){
        map.put("name", "权限2页面");
        return "index";
    }

    @GetMapping("/login-view")
    public String login(){
        return "login";
    }

    //获取当前用户信息
    private String getUsername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if(principal == null){
            return "匿名用户";
        }
        if (principal instanceof UserDetails){
            UserDetails userDetails = (UserDetails) principal;
            return userDetails.getUsername();
        }else {
            return principal.toString();
        }
    }
}
