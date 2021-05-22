package com.xxx.server.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.xxx.server.pojo.Department;
import com.xxx.server.pojo.RespBean;
import io.swagger.models.auth.In;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Wu zicong
 * @since 2021-04-23
 */
public interface IDepartmentService extends IService<Department> {
    /**
     * 获取所有部门
     * @return
     */
    List<Department> getAllDepartments();

    RespBean addDep(Department dep);

    RespBean deleteDep(Integer id);
}
