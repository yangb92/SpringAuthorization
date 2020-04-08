package com.yangb.springsecurity.dao;

import com.yangb.springsecurity.model.PermissionDto;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Created by yangb on 2020/4/8
 */
@Repository
public interface PermissionDao extends CrudRepository<PermissionDto,Integer> {

    @Query("SELECT * FROM `t_permission` WHERE id IN ( SELECT permission_id FROM t_role_permission WHERE role_id IN ( SELECT role_id FROM t_user_role WHERE user_id = :userId ) )")
    List<PermissionDto> findByUserId(@Param("userId") String userId);
}
