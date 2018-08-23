package com.lister.itms.dao.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

/**
 * 系统用户实体类
 */
@Data
public class UserDO implements Serializable {
    
    private Long id;

    private String loginName;

    private Long roleId;
    
    private Long[] roleIds;
    
    private String[] roleNames;

    private String password;

    private String name;
    
    private String headImage;

    private String email;

    private String wechat;

    private String phone;

    private String remarks;

    private Date createDate;

    private String roleName;

    private String address;

    private String newPassword;

    private String isDeleted;

    private String strIds;

    private String strLevel;

    /**
     * 所关联的产品
     */
    private String productName;
    
    /**
     * 查询关键字
     */
    private String queryKeyword;


    @Override
    public String toString() {
        return "UserDO{" +
                "id=" + id +
                ", loginName='" + loginName + '\'' +
                ", roleId=" + roleId +
                ", roleIds=" + Arrays.toString(roleIds) +
                ", roleNames=" + Arrays.toString(roleNames) +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", headImage='" + headImage + '\'' +
                ", email='" + email + '\'' +
                ", wechat='" + wechat + '\'' +
                ", phone='" + phone + '\'' +
                ", remarks='" + remarks + '\'' +
                ", createDate=" + createDate +
                ", roleName='" + roleName + '\'' +
                ", address='" + address + '\'' +
                ", newPassword='" + newPassword + '\'' +
                ", isDeleted='" + isDeleted + '\'' +
                ", strIds='" + strIds + '\'' +
                ", strLevel='" + strLevel + '\'' +
                '}';
    }

}
