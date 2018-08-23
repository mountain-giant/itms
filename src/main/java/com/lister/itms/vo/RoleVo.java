package com.lister.itms.vo;

import lombok.Data;

import java.util.Date;

/**
 * Created by Lister on 2017/6/18.
 */
@Data
public class RoleVo {
    private Long roleId;
    private String roleName;
    private String roleDesc;
    private String name;
    private boolean isHave;    // 是否拥有
    private Date createDate;
}
