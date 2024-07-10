package com.xmut.forum.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xmut.forum.mapper.BlogMapper;
import com.xmut.forum.mapper.UserInfoMapper;
import com.xmut.forum.mapper.UserMapper;
import com.xmut.forum.pojo.Blog;
import com.xmut.forum.pojo.PageResult;
import com.xmut.forum.pojo.UserInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@Service
public class SearchService {

    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private UserMapper userMapper;

    /**
     *搜索
     * @return
     */
    public PageResult<Blog> getBlogsByKey(String key, int page, int rows, Model model){

        if (StringUtils.isEmpty(key)){

            PageHelper.startPage(page, rows);
            List<Blog> blogs = blogMapper.selectAll();

            PageInfo<Blog> pageInfo = new PageInfo<>(blogs);

            return new PageResult<>(pageInfo,pageInfo.getList());

        }else {

            List<Blog> blogs = blogMapper.searchBlogByKey(key);

            if (blogs.size()==0){
                model.addAttribute("msg","暂时没有你想要的内容~");
            }

            PageInfo<Blog> pageInfo = new PageInfo<>(blogs);

            return new PageResult<>(pageInfo,pageInfo.getList());
        }

    }

    public PageResult<UserInfo> getUsersByKey(String key, int page, int rows, Model model){

        if (StringUtils.isEmpty(key)){

            PageHelper.startPage(page, rows);
            List<UserInfo> userInfos = userInfoMapper.selectAll();

            PageInfo<UserInfo> pageInfo = new PageInfo<>(userInfos);

            return new PageResult<>(pageInfo,pageInfo.getList());

        }else {

            List<UserInfo> userInfos = userInfoMapper.getUserByKey(key);

            if (userInfos.size()==0){
                model.addAttribute("msg","暂时没有你想要的内容~");
            }

            PageInfo<UserInfo> pageInfo = new PageInfo<>(userInfos);

            return new PageResult<>(pageInfo,pageInfo.getList());
        }


    }

}
