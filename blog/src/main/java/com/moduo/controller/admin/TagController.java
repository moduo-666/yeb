package com.moduo.controller.admin;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Wu zicong
 * @since 2021-04-29
 */
@Controller

public class TagController {
    @GetMapping("/admin/tags")
public String tags(){
    return "/admin/tags";
}
}
