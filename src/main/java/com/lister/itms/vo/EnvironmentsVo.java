package com.lister.itms.vo;

import lombok.Data;

import java.util.Date;

/**
 * 环境视图实体
 */
@Data
public class EnvironmentsVo {
    
    private Long id;

    private String ip;

    private String environment;

    private String state;

    private Date createTime;

    private String createBy;

    private Date updateTime;

    private String updateBy;

    private String commons;

}
