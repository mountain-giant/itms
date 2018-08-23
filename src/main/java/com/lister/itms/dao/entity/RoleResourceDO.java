package com.lister.itms.dao.entity;

import java.io.Serializable;

public class RoleResourceDO implements Serializable {
    private Long roleId;

    private Long menuId;
    
    private Long userId;
    
    public RoleResourceDO(){
        super();
    }
    
    public RoleResourceDO(Long roleId, Long menuId){
        this.roleId = roleId;
        this.menuId = menuId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }
}
