package com.lister.itms.vo;

/**
 * Describe :
 * Created by Lister<728661851@qq.com/> on 2017/7/7 11:29.
 * Update reason :
 * Updated by Lister<728661851@qq.com/> on 2017/7/7 11:29.
 * Remark : 修改代码请务必加上,修改日期,修改人,修改原因.每次修改都应该出现新的两行 'Updated reason'
 * 以及 'Updateed by XXX on XXXXXXXX XXXXXX',并且修改版本号
 * Version : 1.0
 */
public class DropDownVO {
    private String value;
    private String text;

    public DropDownVO() {
    }

    public DropDownVO(String value, String text) {
        this.value = value;
        this.text = text;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
