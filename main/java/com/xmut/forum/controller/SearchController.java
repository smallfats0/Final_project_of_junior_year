package com.xmut.forum.controller;

import com.xmut.forum.pojo.Blog;
import com.xmut.forum.pojo.PageResult;
import com.xmut.forum.pojo.UserInfo;
import com.xmut.forum.service.SearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SearchController {

    private static final Logger logger = LoggerFactory.getLogger(SearchController.class);

    @Autowired
    private SearchService searchService;


    @GetMapping("/search/{page}/{rows}")
    public String searchGo(
                           @PathVariable("page") int page,
                           @PathVariable("rows") int rows,
                           @RequestParam("title") String key,
                           Model model){
        try {
            PageResult<Blog> blogs = searchService.getBlogsByKey(key, page, rows,model);

            model.addAttribute("blogs", blogs);
            model.addAttribute("key", key);
            return "search";
        } catch (Exception e) {
            logger.info(e.getMessage());
            return "search";
        }
    }

    @GetMapping("/searchU/{page}/{rows}")
    public String searchUser(
            @PathVariable("page") int page,
            @PathVariable("rows") int rows,
            @RequestParam("userNameS") String uKey,
            Model model) {
        try {
            PageResult<UserInfo> userInfos=searchService.getUsersByKey(uKey,page,rows,model);

            model.addAttribute("userInfos", userInfos);
            model.addAttribute("uKey", uKey);

            return "searchU";

        } catch (Exception e) {
            logger.info(e.getMessage());
            return "searchU";
        }
    }

}
