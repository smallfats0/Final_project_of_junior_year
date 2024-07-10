package com.xmut.forum.controller;
import com.xmut.forum.mapper.BlogMapper;
import com.xmut.forum.mapper.UserMapper;
import com.xmut.forum.pojo.Blog;
import com.xmut.forum.pojo.PageResult;
import com.xmut.forum.service.BlogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class AdminController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private BlogMapper blogMapper;
    @Autowired
    private BlogService blogService;
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
    /**
     * 显示管理员后端系统
     */
    @GetMapping("/admin")
    public String index(Model model) {
        model.addAttribute("usercnt", userMapper.getAllUsersCount());
        model.addAttribute("blogcnt", blogMapper.getAllBlogsCount());
        return "admin/index";
    }
    /**
     * 列出所有文章
     */
    @GetMapping("/admin/articles")
    public String listArticles(Model model) {
        try {
            PageResult<Blog> blogPageResult = blogService.getAllBlogs(1, 20);
            model.addAttribute("blogs", blogPageResult);
        } catch (Exception e) {
            logger.error("列出所有文章出现异常：" + e);
            return "admin/pages/articles";
        }
        return "admin/pages/articles";
    }
    /**
     * 分页获取文章
     */
    @GetMapping("/admin/articles/{page}/{rows}")
    public String listArticlesByPage(
        @PathVariable int page,
        @PathVariable int rows,
        Model model
    ) {
        try {
            PageResult<Blog> blogPageResult = blogService.getAllBlogs(page, rows);
            model.addAttribute("blogs", blogPageResult);
        } catch (Exception e) {
            logger.error("分页获取文章出现异常：" + e);
            return "admin/pages/articles";
        }
        return "admin/pages/articles";
    }
}
