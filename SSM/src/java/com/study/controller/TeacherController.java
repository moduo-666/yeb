package com.study.controller;

import com.study.bean.Teacher;
import com.study.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Wu Zicong
 * @create 2021-04-05 12:01
 */
@Controller
public class TeacherController {
    @Autowired
    TeacherService teacherService;
    @RequestMapping("/getTeacher")
    public String getTeacher(@RequestParam(value = "id",defaultValue = "1") Integer id, Model model){
        Teacher teacher = teacherService.getTeacher(id);
        model.addAttribute("teacher",teacher);
        return "success";
    }
}
