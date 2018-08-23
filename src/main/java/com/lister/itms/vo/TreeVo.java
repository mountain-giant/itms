package com.lister.itms.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * Describe :
 * Created by Lister<728661851@qq.com/> on 16/11/22 12:15.
 * Update reason :
 * Updated by Lister<728661851@qq.com/> on 16/11/22 12:15.
 * Remark : 修改代码请务必加上,修改日期,修改人,修改原因.每次修改都应该出现新的两行 'Updated reason'
 * 以及 'Updateed by XXX on XXXXXXXX XXXXXX',并且修改版本号
 * Version : 1.0
 */
@SuppressWarnings("all")
public class TreeVo {

    private Long id;
    
    private Long parentId;
    
    private String iconCls;
    
    private String text;
    
    private boolean checked;
    
    private List<TreeVo> children = new ArrayList<TreeVo>();

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

    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public List<TreeVo> getChildren() {
        return children;
    }

    public void setChildren(List<TreeVo> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "TreeVO{" +
                "id=" + id +
                ", parentId=" + parentId +
                ", iconCls='" + iconCls + '\'' +
                ", text='" + text + '\'' +
                ", checked=" + checked +
                ", children=" + children +
                '}';
    }
}
