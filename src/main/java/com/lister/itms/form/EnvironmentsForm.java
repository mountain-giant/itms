package com.lister.itms.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

/**
 * 环境视图实体
 */
@Data
public class EnvironmentsForm {
    
    private Long id;

    @NotBlank(message = "IP地址不允许为空")
    private String ip;

    @NotBlank(message = "环境名称不允许为空")
    private String environment;

    private String state;

    private String createBy;

    private String updateBy;

}
