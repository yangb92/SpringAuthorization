package com.yangb.security.uaa.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

/**
 * @author Created by yangb on 2020/4/8
 */
@Data
@Table("t_permission")
public class PermissionDto {

    @Id
    private Integer id;
    private String code;
    private String desc;
    private String url;
}
