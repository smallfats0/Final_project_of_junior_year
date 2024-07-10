package com.xmut.forum.service;


import com.xmut.forum.mapper.UserMapper;
import com.xmut.forum.pojo.Blog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

@Service
public class AdminService {
    @Autowired
    private UserMapper userMapper;

    /**
     * 返回所有用户数量
     */
    public Integer getAllUsers() {
        Example example = new Example(Blog.class);
        return userMapper.getAllUsersCount();
    }
    // 查询所有文章
}
