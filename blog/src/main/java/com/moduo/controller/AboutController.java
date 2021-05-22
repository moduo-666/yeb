package com.moduo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Wu Zicong
 * @create 2021-05-06 23:22
 */
@Controller
public class AboutController {
    @GetMapping("/about")
    public String about(){
        return "about";
    }
}
