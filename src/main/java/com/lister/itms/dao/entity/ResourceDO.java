package com.lister.itms.dao.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 菜单实体类
 */
@SuppressWarnings("all")
public class ResourceDO implements Serializable {
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
    
    private List<ResourceDO> children = new ArrayList<ResourceDO>();

    public List<ResourceDO> getChildren() {
        return children;
    }

    public void setChildren(List<ResourceDO> children) {
        this.children = children;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href == null ? null : href.trim();
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
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

    public Boolean getIsselect() {
        return isselect;
    }

    public void setIsselect(Boolean isselect) {
        this.isselect = isselect;
    }

    public String getShowInHeadName() {
        return showInHeadName;
    }

    public void setShowInHeadName(String showInHeadName) {
        this.showInHeadName = showInHeadName;
    }

    public String getIsShowInHead() {
        return isShowInHead;
    }

    public void setIsShowInHead(String isShowInHead) {
        this.isShowInHead = isShowInHead;
    }

    public String getIconColor() {
        return iconColor;
    }

    public void setIconColor(String iconColor) {
        this.iconColor = iconColor;
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }
}
