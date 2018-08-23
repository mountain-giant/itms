package com.lister.itms.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 菜单实体类
 */
@Data
public class ResourceVo implements Serializable {
    private Long id;

    private Long parentId;

    private String name;
    
    private String type;
    
    private String showInHeadName;

    private String href;

    private String icon;

    private Integer sort;

    private String isShowInHead;

    private Long userId;

    private Date createDate;
    
    private String userName;

    private Boolean isselect;
    
    private String iconColor;
    
    private String isDeleted;
    
    private List<ResourceVo> children = new ArrayList<ResourceVo>();

}
