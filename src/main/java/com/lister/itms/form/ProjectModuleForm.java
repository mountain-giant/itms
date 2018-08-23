package com.lister.itms.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ProjectModuleForm {
    
    private Long id;

    @NotNull(message = "项目ID必填")
    private Long projectId;

    @NotBlank(message = "模块名称必填")
    private String moduleName;

    private String moduleDesc;

    private String createBy;
    
    private String updateBy;

    
}
