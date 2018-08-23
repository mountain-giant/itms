package com.lister.itms.dao.entity;

import java.io.Serializable;
import java.util.Date;

public class RoleDO implements Serializable {
    private Long roleId;

    private String roleName;

    private String roleDesc;
    
    private Long userId;

    private Date createDate;
    
    private String name;

    private boolean isHave;    // 是否拥有
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc == null ? null : roleDesc.trim();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public RoleDO() {
    }

    public RoleDO(Long roleId, String roleName, String roleDesc) {
        this.roleId = roleId;
        this.roleName = roleName;
        this.roleDesc = roleDesc;
    }

    public boolean isHave() {
        return isHave;
    }

    public void setHave(boolean have) {
        isHave = have;
    }

    @Override
    public String toString() {
        return "RoleDO{" +
                "roleId=" + roleId +
                ", roleName='" + roleName + '\'' +
                ", userId=" + userId +
                ", createDate=" + createDate +
                ", name='" + name + '\'' +
                '}';
    }
}
