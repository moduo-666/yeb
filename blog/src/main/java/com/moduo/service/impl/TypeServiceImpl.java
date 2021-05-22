package com.moduo.service.impl;

import com.moduo.pojo.Type;
import com.moduo.mapper.TypeMapper;
import com.moduo.service.ITypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Wu zicong
 * @since 2021-04-29
 */
@Service
public class TypeServiceImpl extends ServiceImpl<TypeMapper, Type> implements ITypeService {
    @Autowired
    private TypeMapper typeMapper;
    @Override
    public Type getTypeByName(String name) {
        return typeMapper.getTypeByName(name);
    }
}
