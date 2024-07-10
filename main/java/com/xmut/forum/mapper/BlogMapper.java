package com.xmut.forum.mapper;

import com.xmut.forum.pojo.Blog;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@org.apache.ibatis.annotations.Mapper

public interface BlogMapper extends Mapper<Blog>{
    /**
     * 查询所有文章数量
     * @return
     */
    @Select("select count(*) from blog")
    Integer getAllBlogsCount();

    @Results(id = "resultMap",value = {
            @Result(property = "authorId",column = "author_id"),
            @Result(property = "bid" ,column = "bid"),
            @Result(property = "authorName",column = "author_name"),
            @Result(property = "authorAvatar",column = "author_avatar"),
            @Result(property = "categoryId",column = "category_id"),
            @Result(property = "categoryName",column = "category_name" ),
            @Result(property = "createTime",column = "create_time")
    })
    @Select("select id,bid,title,content,sort,views,author_id,author_name,author_avatar,category_id,category_name,create_time,original " +
            "from blog where match (title,content) against (#{key} in boolean mode)")
    List<Blog> searchBlogByKey(String key);

    @Results(id = "resultMap1",value = {
            @Result(property = "authorId",column = "author_id"),
            @Result(property = "bid" ,column = "bid"),
            @Result(property = "authorName",column = "author_name"),
            @Result(property = "authorAvatar",column = "author_avatar"),
            @Result(property = "categoryId",column = "category_id"),
            @Result(property = "categoryName",column = "category_name" ),
            @Result(property = "createTime",column = "create_time")
    })
    @Select("select id,bid,title,content,sort,views,author_id,author_name,author_avatar,category_id,category_name,create_time,original" +
            " from blog yb where date_sub(CURDATE(),INTERVAL 5 DAY) <= date(create_time) order by create_time desc")
    List<Blog> getNewsBlogs();

    @Results(id = "resultMap10",value = {
            @Result(property = "authorId",column = "author_id"),
            @Result(property = "bid" ,column = "bid"),
            @Result(property = "authorName",column = "author_name"),
            @Result(property = "authorAvatar",column = "author_avatar"),
            @Result(property = "categoryId",column = "category_id"),
            @Result(property = "categoryName",column = "category_name" ),
            @Result(property = "createTime",column = "create_time")
    })
    @Select("select id,bid,title,content,sort,views,author_id,author_name,author_avatar,category_id,category_name,create_time,original" +
            " from blog where category_id=#{type} order by create_time desc")
    List<Blog> getBlogsByType(int type);


}
