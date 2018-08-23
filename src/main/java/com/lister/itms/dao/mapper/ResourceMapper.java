package com.lister.itms.dao.mapper;

import com.github.pagehelper.Page;
import com.lister.itms.dao.entity.ResourceDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 菜单数据操作Mapper
 */
public interface ResourceMapper {
    /**
     * 根据id删除
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 新增菜单
     * @param record
     * @return
     */
    int insert(ResourceDO record);

    /**
     * 飞空验证新增
     * @param record
     * @return
     */
    int insertSelective(ResourceDO record);

    /**
     * 修改菜单项
     * @param record
     * @return
     */
    int updateByPrimaryKey(ResourceDO record);

    /**
     * 获取角色对应的菜单项
     * @param userId
     * @return
     */
    List<ResourceDO> getMenuByRoleId(Long userId);

    /**
     * 获取角色对应的操作权限
     * @param userId
     * @return
     */
    List<ResourceDO> getFunctionByUserId(Long userId);
    
    /**
     * 分页查询
     * @param resourceDO
     * @return
     */
    Page<ResourceDO> listPage(ResourceDO resourceDO);

    /**
     * 获取菜单权限
     * @param role
     * @return
     */
    List<ResourceDO> getMenuForPermissions(@Param("role") Long role);

    /**
     * 获取对应权限的头部菜单
     * @param userId
     * @return
     */
    List<ResourceDO> getHeadMenuByRoleId(Long userId);

    /**
     * 根据连接地址查询菜单
     * @param href
     * @return
     */
    @Deprecated
    List<ResourceDO> getMenuByHref(@Param("href") String href);

    /**
     * 获取所有的菜单
     * @return
     */
    List<ResourceDO> listAll();

    /**
     * 根据Id查询
     * @param id
     * @return
     */
    ResourceDO selectById(Long id);
}
