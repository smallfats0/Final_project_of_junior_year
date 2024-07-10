package com.xmut.forum.pojo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "dzj")
public class BlogConfig {

    // 项目名
    private String name;
    //上传路径
    private static String profile="D:\\Coder\\JavaCource_XMUT\\final_exam\\src\\main\\resources\\static\\upload";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }


    /**
     * 获取上传路径
     */
    public static String getUploadPath()
    {
        return getProfile() + "/upload";
    }

}
