package com.xmut.forum.service;

import com.xmut.forum.mapper.UserRoleMapper;
import com.xmut.forum.pojo.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRoleService {

    @Autowired
    private UserRoleMapper userRoleMapper;

    //根据用户角色id查询用户角色
    public UserRole getRoleByid(Integer id){
        return userRoleMapper.selectByPrimaryKey(id);
    }
}
