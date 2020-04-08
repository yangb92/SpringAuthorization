package com.yangb.springsecurity.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

/**
 * @author Created by yangb on 2020/4/8
 */
@Data
@Table("t_user")
public class UserDto {
    @Id
    private String id;
    private String username;
    private String password;
    private String fullname;
    private String mobile;
}
