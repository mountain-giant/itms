package com.lister.itms.dao.mapper;



import com.lister.itms.dao.entity.RoleUserDO;

import java.util.List;

public interface RoleUserMapper {
    int deleteByPrimaryKey(RoleUserDO key);
    
    void deleteByUserId(Long userId);

    int insert(RoleUserDO record);

    List<Long> getRoleIdsByUserId(Long userId);
}
