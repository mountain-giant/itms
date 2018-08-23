package com.lister.itms.dao.entity;

import java.io.Serializable;

public class RoleUserDO implements Serializable {
    private Long userId;

    private Long roleId;
    

    public RoleUserDO() {
    }

    public RoleUserDO(Long userId, Long roleId) {
        this.userId = userId;
        this.roleId = roleId;
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
}
