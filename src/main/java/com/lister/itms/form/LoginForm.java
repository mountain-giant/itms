package com.lister.itms.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * Describe :
 * Created by Lister on 2018/7/16 3:20 PM.
 * Version : 1.0
 */
@Data
public class LoginForm {

    /**
     * 登录帐号
     */
    @NotEmpty(message = "帐号不能为空")
    private String loginName;

    /**
     * 登录密码
     */
    @NotEmpty(message = "密码不能为空")
    private String password;
    
}
