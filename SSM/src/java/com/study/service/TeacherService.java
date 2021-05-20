package com.study.service;

import com.study.bean.Teacher;
import com.study.dao.TeacherDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Wu Zicong
 * @create 2021-04-05 11:33
 */
@Service
public class TeacherService {
    @Autowired
    private TeacherDao teacherDao;

    public Teacher getTeacher(Integer id) {
        Teacher teacherById = teacherDao.getTeacherById(id);
        return teacherById;
    }
}
