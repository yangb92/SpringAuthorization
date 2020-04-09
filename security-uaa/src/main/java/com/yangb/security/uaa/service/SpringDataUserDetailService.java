package com.yangb.security.uaa.service;

import com.yangb.security.uaa.dao.PermissionDao;
import com.yangb.security.uaa.dao.UserDao;
import com.yangb.security.uaa.model.PermissionDto;
import com.yangb.security.uaa.model.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

/**
 * @author Created by yangb on 2020/4/8
 */
@Service
public class SpringDataUserDetailService implements UserDetailsService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private PermissionDao permissionDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto userDto = userDao.findByUsername(username);
        if(userDto == null){
            // 如果用户查不到, 返回null, 由provider抛出异常
            return null;
        }
        List<PermissionDto> permission = permissionDao.findByUserId(userDto.getId());
        Stream<String> stream = permission.stream().map(item -> item.getCode());
        UserDetails details = User.withUsername(userDto.getUsername()).password(userDto.getPassword()).authorities(stream.toArray(String[]::new)).build();
        return details;
    }
}
