package com.lister.itms.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * Created by Lister on 2017/6/15.
 */
@Data
public class ProjectForm {
    
    private Long id;

    private Long productId;

    @NotBlank(message = "项目名称必填")
    private String projectName;

    @NotBlank(message = "项目简称必填")
    private String projectShortName;

    private String projectDesc;

    private String state;

    private Date createTime;

    private String createBy;

    private Date updateTime;

    private String updateBy;


}
