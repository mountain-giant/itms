package com.lister.itms.biz;


import com.lister.itms.dao.entity.ResourceDO;
import com.lister.itms.dao.entity.RoleResourceDO;
import com.lister.itms.form.ResourceForm;
import com.lister.itms.modal.MyPageInfo;
import com.lister.itms.vo.ResourceVo;
import com.lister.itms.vo.TreeVo;

import java.util.List;

/**
 * Describe :
 * Created by Lister<728661851@qq.com/> on 16/11/10 17:19.
 * Update reason :
 * Updated by Lister<728661851@qq.com/> on 16/11/10 17:19.
 * Remark : 修改代码请务必加上,修改日期,修改人,修改原因.每次修改都应该出现新的两行 'Updated reason'
 * 以及 'Updateed by XXX on XXXXXXXX XXXXXX',并且修改版本号
 * Version : 1.0
 */
@SuppressWarnings("all")
public interface ResourceBiz {
    
    /**
     * 获取菜单信息
     * @param menuId
     * @return
     */
    StringBuffer getMenu(Long menuId);
    
    /**
     * 查询系统菜单信息
     * @param pageInfo
     * @param menu
     * @param user  @return
     */
    MyPageInfo page(MyPageInfo pageInfo, ResourceVo menu);

    /**
     * 新增菜单信息
     * @param Menu
     * @param resource
     */
    void addMenu(ResourceForm resource);

    /**
     * 修改菜单信息
     * @param Menu
     * @param resource
     */
    void updateMenu(ResourceForm resource);

    /**
     * 删除
     * @param id
     */
    void delete(Long id);

    /**
     * 加载菜单权限信息
     * @param roleId
     * @return
     */
    List<TreeVo> getMenuForPermissions(Long roleId);

    /**
     * 获取与角色对应的头部菜单
     * @param id
     * @return
     */
    List<ResourceDO> getHeadMenu(Long id);

    /**
     * 根据地址查询菜单
     * @param href
     * @return
     */
    List<ResourceDO> getMenuByHref(String href);

    /**
     * 查询权限
     * @param roleMenu
     * @return
     */
    RoleResourceDO getRoleMenu(RoleResourceDO roleMenu);

    /**
     * 获取所有的菜单
     * @return
     */
    List<ResourceDO> getAllMenus();

    /**
     * 获取自己拥有权限的菜单
     * @return
     */
    List<ResourceDO> getMySelfMenus(Long roleId);

    /**
     * 获取自己拥有的操作权限
     * @param id
     * @return
     */
    List<ResourceDO> getFunction(Long id);

    /**
     * 菜单详情
     * @param id
     * @return
     */
    ResourceVo getInfo(Long id);
}
