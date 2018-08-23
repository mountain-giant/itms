package com.lister.itms.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 菜单实体类
 */
@Data
public class ResourceForm implements Serializable {
    
    private Long id;

    @NotNull(message = "父级编号不允许为空")
    private Long parentId;

    @NotEmpty(message = "资源名称不允许为空")
    private String name;
    
    @NotEmpty(message = "资源类型不允许为空")
    private String type;
    
    @NotEmpty(message = "资源访问地址不允许为空")
    private String href;

    private String icon;

    private Integer sort;

    private Long userId;

    private String userName;
    
    private String iconColor;
    
}
