package com.lister.itms.dao.mapper;


import com.github.pagehelper.Page;
import com.lister.itms.dao.entity.RoleDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 角色Mapper
 */
@SuppressWarnings("all")
public interface RoleMapper {
    /**
     * 删除角色
     * @param roleId
     * @return
     */
    int deleteByPrimaryKey(Long roleId);

    /**
     * 新增一个角色
     * @param record
     * @return
     */
    int insert(RoleDO record);

    /**
     * 修改角色信息
     * @param record
     * @return
     */
    int updateByPrimaryKey(RoleDO record);

    /**
     * 获取所有角色
     * @return
     * @param userId
     */
    List<RoleDO> getAll(@Param("userId") Long userId);

    /**
     * 查询角色下的用户数量
     * @param role
     * @return
     */
    Long isRoleDownUser(RoleDO role);

    /**
     * 分页查询
     * @param map
     * @return
     */
    Page<RoleDO> listPage(RoleDO map);

    /**
     * 获取每个角色下的用户数量
     * @return
     */
    List<Map<String,Object>> getRoleDownUserCount();

    /**
     * 根据角色名称查询角色信息，验证角色名是否已存在
     * @param role
     * @return
     */
    RoleDO checkExist(RoleDO role);

    /**
     * 根据用户查询用户拥有的角色集
     * @param userId
     * @return
     */
    List<RoleDO> getRoleByUser(Long userId);

    /**
     * 根据角色编号查询角色
     * @param roleId
     * @return
     */
    RoleDO getRoleById(Long roleId);
}
