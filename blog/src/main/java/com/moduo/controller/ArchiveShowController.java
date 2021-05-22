package com.moduo.controller;

import com.moduo.pojo.Blog;
import com.moduo.service.IBlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

/**
 * @author Wu Zicong
 * @create 2021-05-06 21:01
 */
@Controller
public class ArchiveShowController {
    @Autowired
    private IBlogService blogService;
    @GetMapping("/archives")
    public String archives(Model model){
        Map<String, List<Blog>> archiveMap = blogService.archiveBlog();
        int count = blogService.count();
        model.addAttribute("blogCount",count);
        model.addAttribute("archiveMap",archiveMap);
        return "archives";
    }
}
