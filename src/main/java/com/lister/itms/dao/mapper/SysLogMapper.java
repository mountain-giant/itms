/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit. 
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan. 
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna. 
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus. 
 * Vestibulum commodo. Ut rhoncus gravida arcu. 
 */

package com.lister.itms.dao.mapper;


import com.github.pagehelper.Page;
import com.lister.itms.dao.entity.SysLogDO;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface SysLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysLogDO record);

    int insertSelective(SysLogDO record);

    SysLogDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysLogDO record);

    int updateByPrimaryKeyWithBLOBs(SysLogDO record);

    int updateByPrimaryKey(SysLogDO record);

    /**
     * 分页查询系统日志
     * @param value
     * @param startDate
     * @param endDate
     * @return
     */
    Page<SysLogDO> listPage(@Param("value") String value, @Param("startDate") String startDate, @Param("endDate") String endDate);

    int countPage(Map<String, Object> map);
}
