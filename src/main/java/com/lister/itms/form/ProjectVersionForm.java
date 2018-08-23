package com.lister.itms.form;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class ProjectVersionForm {
    
    private Long id;

    @NotNull(message = "项目ID必填")
    private Long projectId;
    
    @NotBlank(message = "版本名称必填")
    private String versionName;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date publishDate;

    private String versionDesc;

    private String state;

    private String createBy;

    private String updateBy;

}
