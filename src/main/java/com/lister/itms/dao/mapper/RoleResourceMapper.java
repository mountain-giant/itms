package com.lister.itms.dao.mapper;



import com.lister.itms.dao.entity.RoleResourceDO;

import java.util.List;

/**
 * 菜单与角色关系Mapper
 */
public interface RoleResourceMapper {
    /**
     * 删除关系
     * @param key
     * @return
     */
    int deleteByPrimaryKey(RoleResourceDO key);

    /**
     * 根据角色删除权限，应用于权限保存
     * @param roleId
     * @return
     */
    int deleteByRoleId(Long roleId);

    /**
     * 新增关系
     * @param record
     * @return
     */
    int insert(RoleResourceDO record);

    /**
     * 查询关系应用于权限验证
     * @param roleAndMenu
     * @return
     */
    RoleResourceDO getRoleMenuForPermissionsCheck(RoleResourceDO roleAndMenu);

    /**
     * 批量新增
     * @param newMenus
     */
    void inserts(List<RoleResourceDO> newMenus);
}
