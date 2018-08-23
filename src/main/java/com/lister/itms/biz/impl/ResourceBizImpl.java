package com.lister.itms.biz.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lister.itms.biz.ResourceBiz;
import com.lister.itms.dao.entity.ResourceDO;
import com.lister.itms.dao.entity.RoleResourceDO;
import com.lister.itms.dao.mapper.ResourceMapper;
import com.lister.itms.dao.mapper.RoleResourceMapper;
import com.lister.itms.form.ResourceForm;
import com.lister.itms.modal.MyPageInfo;
import com.lister.itms.utils.DTOUtils;
import com.lister.itms.vo.ResourceVo;
import com.lister.itms.vo.TreeVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Describe :
 * Created by Lister<728661851@qq.com/> on 16/11/10 17:28.
 * Update reason :
 * Updated by Lister<728661851@qq.com/> on 16/11/10 17:28.
 * Remark : 修改代码请务必加上,修改日期,修改人,修改原因.每次修改都应该出现新的两行 'Updated reason'
 * 以及 'Updateed by XXX on XXXXXXXX XXXXXX',并且修改版本号
 * Version : 1.0
 */
@Slf4j
@Service
public class ResourceBizImpl implements ResourceBiz {

    @Autowired
    private ResourceMapper resourceMapper;

    @Autowired
    private RoleResourceMapper roleResourceMapper;

    /**
     * 获取菜单信息
     *
     * @param roleId
     * @return
     */
    public StringBuffer getMenu(Long roleId) {
        List<ResourceDO> menus = resourceMapper.getMenuByRoleId(roleId);
        StringBuffer sb = new StringBuffer();
        ResourceDO root = new ResourceDO();
        root.setId(0L);
        childrenList(root, menus);
        childrenListForMenu(root.getChildren(), sb);
        return sb;
    }


    /**
     * 查询系统菜单信息
     *
     * @param pageInfo
     * @param menu
     * @param menu
     */
    public MyPageInfo page(MyPageInfo pageInfo, ResourceVo menu) {
        PageHelper.startPage(pageInfo.getPageNumber(),pageInfo.getPageSize());
        ResourceDO resource = DTOUtils.map(menu,ResourceDO.class);
        Page<ResourceDO> page = resourceMapper.listPage(resource);
        List<ResourceVo> resourceVos = DTOUtils.map(page.getResult(), ResourceVo.class);
        pageInfo = MyPageInfo.create(page);
        pageInfo.setRows(resourceVos);
        return pageInfo;
    }

    /**
     * 新增菜单信息
     *
     * @param resource
     */
    public void addMenu(ResourceForm resource) {
        ResourceDO resourceDO = DTOUtils.map(resource,ResourceDO.class); 
        resourceDO.setCreateDate(new Date());
        resourceMapper.insert(resourceDO);
    }

    /**
     * 修改菜单信息
     *
     * @param resource
     */
    public void updateMenu(ResourceForm resource) {
        ResourceDO resourceDO = DTOUtils.map(resource,ResourceDO.class);
        resourceMapper.updateByPrimaryKey(resourceDO);
    }

    /**
     * 删除
     *
     * @param id
     */
    public void delete(Long id) {
        resourceMapper.deleteByPrimaryKey(id);
    }

    /**
     * 获取与角色对应的头部菜单
     *
     * @param id
     * @return
     */
    public List<ResourceDO> getHeadMenu(Long id) {
        List<ResourceDO> headMenus = resourceMapper.getHeadMenuByRoleId(id);
        return headMenus;
    }

    /**
     * 根据地址查询菜单
     *
     * @param href
     * @return
     */
    @Deprecated
    public List<ResourceDO> getMenuByHref(String href) {
        return resourceMapper.getMenuByHref(href);
    }

    /**
     * 查询权限
     *
     * @param roleMenu
     * @return
     */
    public RoleResourceDO getRoleMenu(RoleResourceDO roleMenu) {
        return roleResourceMapper.getRoleMenuForPermissionsCheck(roleMenu);
    }

    /**
     * 获取所有的菜单
     *
     * @return
     */
    @Override
    public List<ResourceDO> getAllMenus() {
        return resourceMapper.listAll();
    }

    /**
     * 获取自己拥有权限的菜单
     *
     * @return
     */
    @Override
    public List<ResourceDO> getMySelfMenus(Long userId) {
        return resourceMapper.getMenuByRoleId(userId);
    }

    /**
     * 获取自己拥有的操作权限
     *
     * @param id
     * @return
     */
    @Override
    public List<ResourceDO> getFunction(Long id) {
        return resourceMapper.getFunctionByUserId(id);
    }

    /**
     * 菜单详情
     *
     * @param id
     * @return
     */
    @Override
    public ResourceVo getInfo(Long id) {
        ResourceDO resourceDO = resourceMapper.selectById(id);
        return DTOUtils.map(resourceDO,ResourceVo.class);
    }


    /**
     * 加载菜单权限信息,应用于EasyUI的Tree
     *
     * @param roleId
     * @return
     */
    public List<TreeVo> getMenuForPermissions(Long roleId) {
        List<ResourceDO> menus = resourceMapper.getMenuForPermissions(roleId);
        List<TreeVo> menuTrees = new ArrayList<TreeVo>();
        // 将Menu实体类的数据转移到MenuTree中
        for (int j = menus.size() - 1; j >= 0; j--) {
            TreeVo menuTree = new TreeVo();
            ResourceDO menu = menus.get(j);
            menuTree.setId(menu.getId());
            menuTree.setParentId(menu.getParentId());
            menuTree.setIconCls(menu.getId() + "");
            menuTree.setChecked(menu.getIsselect());
            if ("M".equals(menu.getType())) {
                menuTree.setText("<span class=\"badge bg-blue\">~</span>" + menu.getName());
            } else {
                menuTree.setText("<span class=\"badge bg-red\">~</span>" + menu.getName());
            }
            menuTrees.add(menuTree);
        }
        TreeVo root = new TreeVo();
        root.setId(0L);
        childrenList(root, menuTrees);
        return root.getChildren();
    }

    /**
     * 使用递归算法将数据结构节点化,应用于 TreeVO
     *
     * @param menu
     * @param menuList
     */
    private void childrenList(TreeVo menu, List<TreeVo> menuList) {
        for (int j = menuList.size() - 1; j >= 0; j--) {
            if (menuList.get(j).getParentId() == menu.getId()) {
                menu.setChecked(false);
                menu.getChildren().add(menuList.get(j));
                menuList.remove(j);
            }
        }
        // 递归循环
        for (TreeVo child : menu.getChildren()) {
            childrenList(child, menuList);
        }
    }

    /**
     * 使用递归算法将数据结构节点化，应用于 ResourceDO
     *
     * @param menu
     * @param menuList
     */
    private void childrenList(ResourceDO menu, List<ResourceDO> menuList) {
        for (int j = menuList.size() - 1; j >= 0; j--) {
            if (menuList.get(j).getParentId() == menu.getId()) {
                menu.getChildren().add(menuList.get(j));
                menuList.remove(j);
            }
        }
        // 递归循环
        for (ResourceDO child : menu.getChildren()) {
            childrenList(child, menuList);
        }
    }

    /**
     * 使用递归拼接HTML代码，应用于左侧菜单
     *
     * @param menuList
     * @param sb
     */
    private void childrenListForMenu(List<ResourceDO> menuList, StringBuffer sb) {
        for (int j = menuList.size() - 1; j >= 0; j--) {
            if (menuList.get(j).getChildren().size() > 0) {
                sb.append("<li class=\"treeview\">");
                if (menuList.get(j).getParentId() != 0L) {
                    sb.append("<a href='javascript:void(0)'>");
                } else {
                    sb.append("<a href='javascript:void(0)' style=\"height:44px;\">");
                }
                sb.append("<i class='" + menuList.get(j).getIcon() + "' style='color:" + menuList.get(j).getIconColor() + "'></i> <span>" + menuList.get(j).getName() + "</span>");
                sb.append("<span class=\"pull-right-container\"><i class=\"fa fa-angle-left pull-right\"></i></span>");
                sb.append("</a>");
            } else {
                sb.append("<li>");
                if (menuList.get(j).getParentId() != 0L) {
                    sb.append("<a href='javascript:void(0)' url='" + menuList.get(j).getHref() + "' class='menu_link'>");
                } else {
                    sb.append("<a href='javascript:void(0)' style=\"height:44px;\" url='" + menuList.get(j).getHref() + "' class='menu_link'>");
                }
                sb.append("<i class='" + menuList.get(j).getIcon() + "' style='color:" + menuList.get(j).getIconColor() + "'></i> <span>" + menuList.get(j).getName() + "</span>");
                sb.append("</a>");
            }
            if (menuList.get(j).getChildren().size() > 0) {
                sb.append("<ul class=\"treeview-menu\">");
                childrenListForMenu(menuList.get(j).getChildren(), sb);
                sb.append("</ul>");
            }
            sb.append("</li>");
        }
    }
}
