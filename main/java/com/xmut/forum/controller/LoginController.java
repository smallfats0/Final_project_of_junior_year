package com.xmut.forum.controller;

import com.xmut.forum.constants.Constants;
import com.xmut.forum.pojo.RegisterForm;
import com.xmut.forum.pojo.User;
import com.xmut.forum.pojo.UserInfo;
import com.xmut.forum.service.UserInfoService;
import com.xmut.forum.service.UserService;
import com.xmut.forum.util.RedisUtil;
import com.xmut.forum.util.BasicUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private RedisUtil redisUtil;

    @Resource
    private AuthenticationManager authenticationManager;



    /**
     * 用户注册
     */
    @PostMapping("/register")
    public String register(@Validated RegisterForm registerForm, BindingResult result, Model model, HttpServletRequest request) {

        try {
            //判断校验是否有错
            if (result.hasErrors()){
                return "register";
            }
            //判断密码是否一致
            if (!registerForm.getPassword().equals(registerForm.getRepassword())){
                model.addAttribute("pmsg", "密码不一致");
                return "register";
            }

            //获取验证码
            String rediscode = (String)redisUtil.get(Constants.CAPTCHA_CODE_KEY + request.getRemoteAddr() + ":" +registerForm.getUsername());
            //判断redis中是否有验证码
            if (StringUtils.isEmpty(rediscode)){
                model.addAttribute("emsg", "验证码错误");
                return "register";
            }
            //转为小写
            rediscode = rediscode.toLowerCase();
            //判断验证码是否一致
            if (!rediscode.equals(registerForm.getVerifycode().toLowerCase())){
                model.addAttribute("username", registerForm.getUsername());
                model.addAttribute("emsg", "验证码错误");
                return "register";
            } else {
                User user = new User();
                user.setUid(BasicUtils.getUuid());
                user.setRoleId(2);
                user.setUsername(registerForm.getUsername());
                //密码加密
                String password = new BCryptPasswordEncoder().encode(registerForm.getPassword());
                user.setPassword(password);
                user.setAvatar("/images/avatar/man.jpg");
                user.setCreateTime(BasicUtils.getTime());
                user.setLoginTime(BasicUtils.getTime());

                //保存用户账号信息
                userService.saveUser(user);

                // todo: 保存用户个人信息
                UserInfo userInfo = new UserInfo();
                userInfo.setUid(user.getUid());
                userInfo.setNickname("轻轻的草原");
                userInfo.setEmail(registerForm.getEmail());
                userInfoService.SaveUserInfo(userInfo);

                model.addAttribute("msg","注册成功，快去登录吧~");
                //注册成功，重定向到登录页面
                return "register";
            }
        } catch (Exception e) {
            logger.error("注册失败"+e);
            model.addAttribute("emsg", "服务器错误，请稍后再试");
            return "register";
        }
    }

    @PostMapping("/findpwd")
    public String findPwd(@Validated RegisterForm registerForm, BindingResult result, Model model,HttpServletRequest request){
        try {
            //判断校验是否有错
            if (result.hasErrors()){
                return "find";
            }
            //判断密码是否一致
            if (!registerForm.getPassword().equals(registerForm.getRepassword())){
                model.addAttribute("pmsg", "密码不一致");
                return "find";
            }

            //获取验证码
            String rediscode = (String)redisUtil.get(Constants.CAPTCHA_CODE_KEY + request.getRemoteAddr() + ":" + registerForm.getUsername());
            //判断redis中是否有验证码
            if (StringUtils.isEmpty(rediscode)){
                model.addAttribute("emsg", "验证码错误");
                return "find";
            }
            //转为小写
            rediscode = rediscode.toLowerCase();
            //判断验证码是否一致
            if (!rediscode.equals(registerForm.getVerifycode().toLowerCase())){
                model.addAttribute("username", registerForm.getUsername());
                model.addAttribute("emsg", "验证码错误");
                return "find";
            }

            Boolean okEmail = isOkEmail(registerForm.getUsername(), registerForm.getEmail());
            if (!okEmail) {
                model.addAttribute("emailmsg", "邮箱不一致");
                return "find";
            }else {
                User user = new User();
                user.setUsername(registerForm.getUsername());
                String password = new BCryptPasswordEncoder().encode(registerForm.getPassword());
                user.setPassword(password);

                userService.changePwd(user);
                model.addAttribute("msg", "修改成功，快去登录吧~");
                //注册成功，重定向到登录页面
                return "find";
            }
        } catch (Exception e) {
            logger.info("找回密码失败"+e.getMessage());
            model.addAttribute("emsg", "服务器错误，请稍后再试");
            return "find";
        }
    }

    /**
     * 判断是否是注册的邮箱
     */
    public Boolean isOkEmail(String username,String email){
        return userInfoService.isExistByUnameAndEmail(username, email);
    }

    /**
     * 判断用户名是否已使用
     */
    @GetMapping("/checkusername")
    @ResponseBody
    public String checkUsername(@RequestParam("username") String username){

        User user = userService.queryUserByName(username);
        if(user != null){
            return "用户名已使用";
        }
        return null;
    }
}
