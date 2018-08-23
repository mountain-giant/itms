/*
 * Copyright (c) 2017. Lorem ipsum dolor sit amet, consectetur adipiscing elit. 
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan. 
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna. 
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus. 
 * Vestibulum commodo. Ut rhoncus gravida arcu. 
 */

package com.lister.itms.enums;

/**
 * Describe :
 * Created by Lister<728661851@qq.com/> on 2017/8/14 20:00.
 * Update reason :
 * Updated by Lister<728661851@qq.com/> on 2017/8/14 20:00.
 * Remark : 修改代码请务必加上,修改日期,修改人,修改原因.每次修改都应该出现新的两行 'Updated reason'
 * 以及 'Updateed by XXX on XXXXXXXX XXXXXX',并且修改版本号
 * Version : 1.0
 */
public enum SysLogTypeEnums {
    OPER(1,"操作"),
    VIEW(2,"页面"),
    LOGIN(3,"登录");

    public int code;
    
    public String desc;

    SysLogTypeEnums(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
