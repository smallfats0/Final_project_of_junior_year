package com.xmut.forum.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OtherController {
    @GetMapping("/resources")
    public String toResources(){
        return "page/resources";
    }
    @GetMapping("/resources-show")
    public String toResourcesShow(){
        return "page/resources-show";
    }
    @GetMapping("/videos")
    public String toVideos(){
        return "page/videos";
    }
    @GetMapping("/videos-show")
    public String toVideosShow(){
        return "page/videos-show";
    }
    @GetMapping("/videos-show-lecture")
    public String toVideosShowLecture(){
        return "page/videos-show-lecture";
    }
    @GetMapping("/docs")
    public String toDocs(){
        return "page/docs";
    }
    @GetMapping("/docs-show")
    public String toDocsShow(){
        return "page/docs-show";
    }
}
