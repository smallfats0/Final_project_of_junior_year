package com.xmut.forum.mapper;

import com.xmut.forum.pojo.UserInfo;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface UserInfoMapper extends Mapper<UserInfo> {

    @Select("select nickname from user_info where uid = #{uid}")
    String getNickByUid(String uid);

    @Select("select * from user_info where nickname like concat('%',#{key},'%') ")
    List<UserInfo> getUserByKey(String key);
}
