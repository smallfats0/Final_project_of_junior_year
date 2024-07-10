package com.xmut.forum.mapper;

import com.xmut.forum.pojo.User;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface UserMapper extends Mapper<User> {
    /**
     * 查询所有用户数量
     */
    @Select("select count(*) from user")
    Integer getAllUsersCount();
}
