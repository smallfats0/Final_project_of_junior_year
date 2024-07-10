package com.xmut.forum.controller;

import com.xmut.forum.pojo.Blog;
import com.xmut.forum.pojo.FollowFans;
import com.xmut.forum.pojo.PageResult;
import com.xmut.forum.service.BlogService;
import com.xmut.forum.service.FollowFansService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class PageController {

    private static final Logger logger = LoggerFactory.getLogger(PageController.class);

    @Autowired
    private BlogService blogService;


    @Autowired
    FollowFansService followFansService;

    @Autowired
    private HttpSession session;

    @GetMapping("/discover/{type}")
    public String pageDiscover1(@PathVariable String type,Model model){

        PageResult<Blog> blogs = blogService.getAllBlogs(1, 20,type);

        model.addAttribute("blogs", blogs);
        session.setAttribute("type",type);
        return "page/discover";
    }

    /**
     * 发现页  分页
     */
    @GetMapping("/discover/{type}/{page}/{rows}")
    public String pageDiscover1(
            @PathVariable String type,
            @PathVariable int page,
            @PathVariable int rows,
            Model model){
        PageResult<Blog> blogs = blogService.getAllBlogs(page, rows,type);
        session.setAttribute("type",type);
        model.addAttribute("blogs", blogs);
        return "page/discover";
    }

    /**
     * 关注页
     */
    @GetMapping("/tofollow")
    public String pageFollow(){
        return "page/follows";
    }

    @GetMapping("/tofollow/{uid}")
    public String pageFollow1(@PathVariable String uid,
                              Model model){
        try {
            List<FollowFans> follows = followFansService.getAllFollow(uid);
            ArrayList<String> list = new ArrayList<>();
            for (FollowFans follow : follows) {
                list.add(follow.getFollowerId());
            }
            PageResult<Blog> blogs = blogService.getBlogsByUids(list,1,20);
            model.addAttribute("blogs", blogs);
            return "page/follows";
        } catch (Exception e) {
            logger.info("失败"+e.getMessage());
            return "page/follows";
        }
    }

    /**
     *  关注页-----分页
     * @return
     */
    @GetMapping("/tofollow/{uid}/{page}/{rows}")
    public String pageFollow2(@PathVariable String uid,
                              @PathVariable int page,
                              @PathVariable int rows,
                              Model model){
        try {
            List<FollowFans> follows = followFansService.getAllFollow(uid);
            ArrayList<String> list = new ArrayList<>();
            for (FollowFans follow : follows) {
                list.add(follow.getFollowerId());
            }
            PageResult<Blog> blogs = blogService.getBlogsByUids(list,page,rows);
            model.addAttribute("blogs", blogs);
            return "page/follows";
        } catch (Exception e) {
            logger.info(e.getMessage());
            return "page/follows";
        }
    }

    /**
     * 最新内容
     */
    @GetMapping("/newblog")
    public String lastBlog(Model model){
        try {
            PageResult<Blog> blogs = blogService.getNewsBlogs(1,20);
            model.addAttribute("blogList", blogs);
            model.addAttribute("class", "two");
            return "page/newblog";
        } catch (Exception e) {
            logger.info(e.getMessage());
            return "page/newblog";
        }
    }


    /**
     * 最新内容---分页
     */
    @GetMapping("/newblog/{page}/{rows}")
    public String lastBlog(@PathVariable int page,@PathVariable int rows, Model model){
        try {
            PageResult<Blog> blogs = blogService.getNewsBlogs(page,rows);
            model.addAttribute("blogList", blogs);
            model.addAttribute("class", "two");
            return "page/newblog";
        } catch (Exception e) {
            logger.info(e.getMessage());
            return "page/newblog";
        }
    }

    /**
     * 最新内容
     */
    @GetMapping("/interest")
    public String interest(Model model){
        try {
            PageResult<Blog> blogs = blogService.getNewsBlogs(1,20);
            model.addAttribute("blogs", blogs);
            model.addAttribute("class", "four");
            return "page/interest";
        } catch (Exception e) {
            logger.info(e.getMessage());
            return "page/interest";
        }
    }

    /**
     * 最新内容---分页
     */
    @GetMapping("/interest/{page}/{rows}")
    public String interest(@PathVariable int page,@PathVariable int rows, Model model){
        try {
            PageResult<Blog> blogs = blogService.getNewsBlogs(page,rows);
            model.addAttribute("blogs", blogs);
            model.addAttribute("class", "four");
            return "page/interest";
        } catch (Exception e) {
            logger.info(e.getMessage());
            return "page/interest";
        }
    }

}
