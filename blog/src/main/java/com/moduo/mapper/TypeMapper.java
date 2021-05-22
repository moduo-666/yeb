package com.moduo.mapper;

import com.moduo.pojo.Type;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Wu zicong
 * @since 2021-04-29
 */
public interface TypeMapper extends BaseMapper<Type> {

    Type getTypeByName(String name);

}
