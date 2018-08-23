package com.lister.itms.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * Describe :
 * Created by Lister on 2018/7/17 2:57 PM.
 * Version : 1.0
 */
@Data
public class RoleForm {
    
    @NotEmpty(message = "角色名称不可为空")
    private String roleName;
    
    private String roleDesc; 
    
    @NotEmpty(message = "权限必选")
    private String menuIds;
    
    @NotEmpty(message = "权限必选")
    private String menuChecks;
    
    /** 操作人编号 */
    private Long userId;
    
    /** 角色编号 */
    private Long roleId;
}
