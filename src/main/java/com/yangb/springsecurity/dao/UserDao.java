package com.yangb.springsecurity.dao;

import com.yangb.springsecurity.model.UserDto;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Created by yangb on 2020/4/8
 */
@Repository
public interface UserDao extends CrudRepository<UserDto,Long> {

    @Query("SELECT * FROM `t_user` where username=:username")
    UserDto findByUsername(@Param("username") String username);

}
