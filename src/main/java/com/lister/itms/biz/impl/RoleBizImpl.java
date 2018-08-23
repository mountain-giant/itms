package com.lister.itms.biz.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lister.itms.biz.RoleBiz;
import com.lister.itms.dao.entity.ResourceDO;
import com.lister.itms.dao.entity.RoleDO;
import com.lister.itms.dao.entity.RoleResourceDO;
import com.lister.itms.dao.mapper.RoleMapper;
import com.lister.itms.dao.mapper.RoleResourceMapper;
import com.lister.itms.exception.BizException;
import com.lister.itms.form.RoleForm;
import com.lister.itms.modal.MyPageInfo;
import com.lister.itms.utils.DTOUtils;
import com.lister.itms.vo.RoleVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Describe :
 * Created by Lister<728661851@qq.com/> on 16/11/12 20:37.
 * Update reason :
 * Updated by Lister<728661851@qq.com/> on 16/11/12 20:37.
 * Remark : 修改代码请务必加上,修改日期,修改人,修改原因.每次修改都应该出现新的两行 'Updated reason'
 * 以及 'Updateed by XXX on XXXXXXXX XXXXXX',并且修改版本号
 * Version : 1.0
 */
@Slf4j
@Service
public class RoleBizImpl implements RoleBiz {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleResourceMapper roleResourceMapper;

    /**
     * 查询所有的Role角色
     *
     * @param userId
     * @return
     */
    @Override
    public List<RoleVo> getAllRole(Long userId) {
        List<RoleDO> roleDOS = roleMapper.getAll(userId);
        List<RoleVo> roleVos = DTOUtils.map(roleDOS, RoleVo.class);
        return roleVos;
    }

    /**
     * 查询系统角色信息
     *
     * @param pageInfo
     * @param roleVo
     */
    @Override
    public MyPageInfo<RoleVo> page(MyPageInfo pageInfo, RoleVo roleVo) {
        RoleDO roleDO = DTOUtils.map(roleVo, RoleDO.class);
        PageHelper.startPage(pageInfo.getPageNumber(), pageInfo.getPageSize());
        Page<RoleDO> page = roleMapper.listPage(roleDO);
        List<RoleVo> roleVos = DTOUtils.map(page.getResult(), RoleVo.class);
        pageInfo = MyPageInfo.create(page);
        pageInfo.setRows(roleVos);
        return pageInfo;
    }

    /**
     * 删除
     *
     * @param id
     */
    public void delete(Long id) {
        RoleDO role = new RoleDO();
        role.setRoleId(id);
        Long count = roleMapper.isRoleDownUser(role);
        if (count > 0)
            throw new BizException("该角色下已有用户，无法删除，请先删除角色下的用户");
        // 删除用户的菜单权限配置
        roleResourceMapper.deleteByRoleId(id);
        roleMapper.deleteByPrimaryKey(id);
    }

    /**
     * 获取角色下用户数量
     *
     * @return
     */
    public List<Map<String, Object>> getRoleDownUserCount() {
        List<Map<String, Object>> results = roleMapper.getRoleDownUserCount();
        List<Map<String, Object>> newResults = new ArrayList<Map<String, Object>>();
        for (Map map : results) {
            Map<String, Object> mapResult = new HashMap<String, Object>();
            mapResult.put("label", map.get("rolename"));
            mapResult.put("value", map.get("nums"));
            newResults.add(mapResult);
        }
        return newResults;
    }

    /**
     * 根据用户查询用户拥有的角色名称
     * 提供给前端主界面显示
     *
     * @param userId
     * @return 以数组的方式返回
     */
    public String getRoleByUser(Long userId) {
        List<RoleDO> roles = roleMapper.getRoleByUser(userId);
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < roles.size(); i++) {
            sb.append(roles.get(i).getRoleName()).append(",");
        }
        String roleName = sb.toString().substring(0, sb.length() - 1);
        if (roleName.length() > 12) {
            roleName = roleName.substring(0, 12) + "...";
        }
        return roleName;
    }

    /**
     * 根据Id查询角色信息
     *
     * @param roleId
     * @return
     */
    @Override
    public RoleVo getRoleById(Long roleId) {
        RoleDO roleDO = roleMapper.getRoleById(roleId);
        return DTOUtils.map(roleDO,RoleVo.class);
    }

    /**
     * 新增角色
     *
     * @param roleForm
     */
    @Override
    public void addRole(RoleForm roleForm) {
        // 组合菜单权限数据
        String[] menuIdarr = roleForm.getMenuIds().split(",");
        String[] menuCheckedsarr = roleForm.getMenuChecks().split(",");
        List<ResourceDO> menus = new LinkedList<>();
        for (int i = 0; i < menuIdarr.length; i++) {
            ResourceDO menu = new ResourceDO();
            menu.setId(Long.parseLong(menuIdarr[i]));
            menu.setIsselect(menuCheckedsarr[i].equals("true") ? true : false);
            menus.add(menu);
        }
        RoleDO role = DTOUtils.map(roleForm, RoleDO.class);
        if (roleMapper.checkExist(role) != null) {
            throw new BizException("已有相同名字的角色");
        }
        role.setCreateDate(new Date());
        roleMapper.insert(role);
        // 新增角色完成后，根据角色名称查询角色的编号
        Long roleId = role.getRoleId();
        // 保存菜单权限信息
        List<RoleResourceDO> newMenus = new ArrayList<>();
        for (ResourceDO m : menus) {
            if (m.getIsselect()) {
                RoleResourceDO roleAndMenu = new RoleResourceDO(roleId, m.getId());
                newMenus.add(roleAndMenu);
            }
        }
        if (newMenus.size() > 0)
            roleResourceMapper.inserts(newMenus);
    }

    /**
     * 编辑角色
     *
     * @param roleForm
     */
    @Override
    public void updateRole(RoleForm roleForm) {
        // 组合菜单权限数据
        String[] menuIdarr = roleForm.getMenuIds().split(",");
        String[] menuCheckedsarr = roleForm.getMenuChecks().split(",");
        List<ResourceDO> menus = new LinkedList<>();
        for (int i = 0; i < menuIdarr.length; i++) {
            ResourceDO menu = new ResourceDO();
            menu.setId(Long.parseLong(menuIdarr[i]));
            menu.setIsselect(menuCheckedsarr[i].equals("true") ? true : false);
            menus.add(menu);
        }
        RoleDO role = DTOUtils.map(roleForm, RoleDO.class);
        if (roleMapper.checkExist(role) != null) {
            throw new BizException("已有相同名字的角色");
        }
        roleMapper.updateByPrimaryKey(role);
        // 保存菜单权限信息
        roleResourceMapper.deleteByRoleId(role.getRoleId());
        List<RoleResourceDO> newMenus = new ArrayList<>();
        for (ResourceDO m : menus) {
            if (m.getIsselect()) {
                RoleResourceDO roleAndMenu = new RoleResourceDO(role.getRoleId(), m.getId());
                newMenus.add(roleAndMenu);
            }
        }
        roleResourceMapper.inserts(newMenus);
    }

}
