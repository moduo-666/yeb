package com.moduo.service;

import com.moduo.pojo.Type;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Wu zicong
 * @since 2021-04-29
 */
public interface ITypeService extends IService<Type> {
    Type getTypeByName(String name);


    /*
    保存
    获取
    分页
    更新
    删除
     */

}
