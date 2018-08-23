package com.lister.itms.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * Describe :
 * Created by Lister on 2018/7/17 10:53 AM.
 * Version : 1.0
 */
@Data
public class UserInfoForm {
    
    private Long id;

    @NotEmpty(message = "请输入账号")
    private String loginName;

    private String[] processRoles;

    private Long[] roleIds;

    private Long[] productIds;

    private String password;

    @NotEmpty(message = "请输入用户姓名")
    private String name;

    @NotEmpty(message = "请输入邮箱")
    private String email;

    @NotEmpty(message = "请输入联系电话")
    private String phone;

    private String headImage;
    
    private String remarks;
}
