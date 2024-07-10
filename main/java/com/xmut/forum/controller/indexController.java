package com.xmut.forum.controller;

import com.xmut.forum.pojo.Blog;
import com.xmut.forum.pojo.PageResult;
import com.xmut.forum.pojo.RegisterForm;
import com.xmut.forum.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class indexController {
    @Autowired
    private BlogService blogService;

    @GetMapping("/register")
    public String toRegister(){
        return "register";
    }

    @GetMapping({"/","/index"})
    public String index(Model model){
        PageResult<Blog> blogs = blogService.getNewsBlogs(1,20);
        model.addAttribute("class", "one");
        model.addAttribute("blogList", blogs);
        return "index";
    }

    @GetMapping("/login")
    public String UserLogin(){
        return "login";

    }

    @GetMapping("/find")
    public String toFindPassword(){
        return "find";
    }

    @ModelAttribute("registerForm")
    public RegisterForm registerForm(){
        return new RegisterForm();
    }

}
